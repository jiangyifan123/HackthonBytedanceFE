package com.andyfan.hackathon

interface Downloader {
    fun downloadFile(url: String): Long
}