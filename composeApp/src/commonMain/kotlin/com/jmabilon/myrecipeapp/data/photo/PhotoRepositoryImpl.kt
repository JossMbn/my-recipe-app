package com.jmabilon.myrecipeapp.data.photo

import com.jmabilon.myrecipeapp.core.network.extension.safeExecution
import com.jmabilon.myrecipeapp.domain.photo.model.PhotoInfoDomain
import com.jmabilon.myrecipeapp.domain.photo.repository.PhotoRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.storage.storage
import kotlin.time.Duration.Companion.hours
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class PhotoRepositoryImpl(
    private val supabaseClient: SupabaseClient
) : PhotoRepository {

    override suspend fun uploadPhoto(
        bucketId: String,
        photoBytes: ByteArray,
        fileName: String?
    ): Result<String> {
        return supabaseClient.safeExecution {
            val finalFileName = fileName ?: "${Uuid.random()}.jpg"

            // Récupérer l'ID de l'utilisateur connecté
            val userId = auth.currentUserOrNull()?.id
                ?: throw IllegalStateException("User must be authenticated to upload photos")

            // Path dans le bucket: {userId}/{fileName}
            val filePath = "$userId/$finalFileName"

            // Upload vers Supabase Storage
            storage.from(bucketId).upload(
                path = filePath,
                data = photoBytes,
                options = { upsert = false }
            )

            filePath
        }
    }

    override suspend fun uploadPhotos(
        bucketId: String,
        photos: List<ByteArray>
    ): Result<List<String>> = runCatching {
        photos.mapIndexed { index, photoBytes ->
            val fileName = "${Uuid.random()}_$index.jpg"
            uploadPhoto(bucketId, photoBytes, fileName).getOrThrow()
        }
    }

    override suspend fun deletePhoto(bucketId: String, filePath: String): Result<Unit> {
        return supabaseClient.safeExecution {
            storage.from(bucketId).delete(filePath)
        }
    }

    override suspend fun updatePhoto(
        bucketId: String,
        oldFilePath: String,
        newPhotoBytes: ByteArray
    ): Result<String> = runCatching {
        // Supprimer l'ancienne photo
        deletePhoto(bucketId, oldFilePath).getOrThrow()

        // Upload la nouvelle
        uploadPhoto(bucketId, newPhotoBytes).getOrThrow()
    }

    override suspend fun listUserPhotos(): Result<List<PhotoInfoDomain>> {
        val recipeBucketId = "recipe-photos"

        return supabaseClient.safeExecution {
            val userId = supabaseClient.auth.currentUserOrNull()?.id
                ?: throw IllegalStateException("User must be authenticated")

            val files = storage.from(recipeBucketId).list(userId)

            files.map { file ->
                val filePath = "$userId/${file.name}"
                PhotoInfoDomain(
                    name = file.name,
                    path = filePath,
                    signedUrl = storage.from(recipeBucketId).createSignedUrl(
                        path = filePath,
                        expiresIn = 24.hours
                    )
                )
            }
        }
    }
}
