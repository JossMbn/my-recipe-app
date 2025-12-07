package com.jmabilon.myrecipeapp.domain.photo.repository

import com.jmabilon.myrecipeapp.domain.photo.model.PhotoInfoDomain

interface PhotoRepository {
    /**
     * Upload une photo depuis un ByteArray (pour Android Camera/Gallery)
     *
     * @param photoBytes Les bytes de l'image
     * @param fileName Nom du fichier (optionnel, généré automatiquement si null)
     * @return URL signée de l'image uploadée (valide 24h)
     */
    suspend fun uploadPhoto(photoBytes: ByteArray, fileName: String? = null): Result<String>

    /**
     * Upload plusieurs photos en une seule opération
     *
     * @param photos Liste de ByteArray représentant les images
     * @return Liste des URLs signées des images uploadées
     */
    suspend fun uploadPhotos(
        photos: List<ByteArray>
    ): Result<List<String>>

    /**
     * Supprime une photo du storage
     *
     * @param filePath Chemin du fichier (format: {userId}/{fileName})
     */
    suspend fun deletePhoto(filePath: String): Result<Unit>

    /**
     * Met à jour une photo existante
     *
     * @param oldFilePath Chemin de l'ancienne photo (sera supprimée)
     * @param newPhotoBytes Bytes de la nouvelle photo
     * @return URL signée de la nouvelle photo
     */
    suspend fun updatePhoto(
        oldFilePath: String,
        newPhotoBytes: ByteArray
    ): Result<String>

    /**
     * Liste toutes les photos de l'utilisateur connecté avec leurs URLs signées
     */
    suspend fun listUserPhotos(): Result<List<PhotoInfoDomain>>
}
