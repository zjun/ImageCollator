package com.zjun.utils;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifInteropDirectory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.ExifThumbnailDirectory;

import java.io.File;
import java.io.IOException;

public class ImageCollater {

    public static void main(String[] args) throws JpegProcessingException, IOException {
        //获取该图片文件
        File file = new File("D:/photo/100.jpg");
        Metadata metadata = JpegMetadataReader.readMetadata(file);
        //输出所有附加属性数据
        for (Directory directory : metadata.getDirectories()) {
            System.out.println("属性组：" + directory.getName());
            for (Tag tag : directory.getTags()) {
                String kv =  String.format("%s = %s",tag.getTagName(),tag.getDescription());
                System.out.println(kv);
            }
        }

        //获取EXIF信息，用直接相机或者手机拍出来的图片来做获取，剪切图或者操作过裁剪大部分信息是读取不到的
        System.out.println("++++++++++++++++++ EXIF ++++++++++++++++++++");
        ExifIFD0Directory exif = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        if(null != exif){
            //循环输出
            exif.getTags().forEach(System.out::println);
            if(exif.containsTag(ExifIFD0Directory.TAG_MAKE)){
                System.out.println("Make:" + exif.getDescription(ExifIFD0Directory.TAG_MAKE));
            }
        }
        System.out.println("ExifSubIFDDirectory：");
        Directory exifSub = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        if(null != exifSub){
            //循环输出
            exifSub.getTags().forEach(System.out::println);
        }
        System.out.println("ExifInteropDirectory：");
        Directory exifInterop = metadata.getFirstDirectoryOfType(ExifInteropDirectory.class);
        if(null != exifInterop){
            //循环输出
            exifInterop.getTags().forEach(System.out::println);
        }
        System.out.println("ExifThumbnailDirectory：");
        Directory exifThumbnail = metadata.getFirstDirectoryOfType(ExifThumbnailDirectory.class);
        if(null != exifThumbnail){
            //循环输出
            exifThumbnail.getTags().forEach(System.out::println);
        }
    }
}
