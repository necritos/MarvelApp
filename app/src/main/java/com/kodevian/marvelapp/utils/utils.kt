package com.kodevian.marvelapp.utils

import java.security.NoSuchAlgorithmException


fun md5(s: String): String {
    try {
        val digest = java.security.MessageDigest.getInstance("MD5")
        digest.update(s.toByteArray())
        val hash = (Strings.hexEncode(digest.digest())).toString()

        return hash
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}