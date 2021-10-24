package com.github.gzhola.okjob.common.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Hola
 * @since 2021-10-24
 */
@Slf4j
@UtilityClass
public class FileUtils {

    /**
     * 递归删除
     *
     * @param root  文件或文件夹路径
     * @return      删除结果
     */
    public static boolean deleteRecursively(File root) {
        if (root != null && root.exists()) {
            if (root.isDirectory()) {
                File[] children = root.listFiles();
                if (children != null) {
                    for (File child : children) {
                        deleteRecursively(child);
                    }
                }
            }
            return root.delete();
        }
        return false;
    }


    /**
     * 删除文件
     *
     * @param fileName  文件全路径
     */
    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     *
     *
     * @param file
     * @param data
     */
    public static void writeFileContent(File file, byte[] data) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 读取文件数据
     *
     * @param file
     * @return
     */
    public static byte[] readFileContent(File file) {
        long fileLength = file.length();
        byte[] fileContent = new byte[(int) fileLength];

        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
            return fileContent;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
