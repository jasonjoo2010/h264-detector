# Introduction

h264-detector is used to determine whether a video specified by file / url is in h264 format.

It's fast as it doesn't load entire video into memory. So it's useful to do detection instead of `ffmpeg`.

```shell
mvn clean package
java -jar target/h264-detector-0.0.1-jar-with-dependencies.jar <location>
```

# Reference

[0] An Efficient Way to Detect h264 Video Format https://jasonjoo2010.github.io/2020/06/22/how_to_detect_h264_video_format.html  
[1] ISO/IEC base media file format https://en.wikipedia.org/wiki/ISO/IEC_base_media_file_format    
[2] ISO 14496-1 Media Format [references/mp4-layout.txt](references/mp4-layout.txt)  
[3] boxes tree [references/boxes.jpg](references/boxes.jpg)  
