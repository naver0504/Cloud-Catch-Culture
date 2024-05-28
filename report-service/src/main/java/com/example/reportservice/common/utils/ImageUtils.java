package com.example.reportservice.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public final class ImageUtils {

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("jpeg", "png", "jpg");


    public static boolean validateImage(final List<MultipartFile> files) {

        if(!validateFileSize(files) || !validateFileContentType(files)) {
            return false;
        }

        return true;
    }

    private static boolean validateFileSize(final List<MultipartFile> files) {
        if(files == null || files.isEmpty()) {
            return false;
        }
        return true;
    }

    private static boolean validateFileContentType(final List<MultipartFile> files) {

        for (final MultipartFile file : files) {
            if(!ALLOWED_CONTENT_TYPES.contains(getFileContentType(file.getContentType()))) {
                return false;
            }
        }

        return true;
    }

    public static String getFileContentType(String contentType) {
        return switch (contentType) {
            case "image/jpeg" -> "jpg";
            case "image/png" -> "png";
            case "image/gif" -> "gif";
            default -> "jpg";
        };
    }

    public static String getImageFileUrlFromStoredFileUrl(final String storedFileUrl) {
        return storedFileUrl.split("/")[storedFileUrl.split("/").length - 1];
    }
}
