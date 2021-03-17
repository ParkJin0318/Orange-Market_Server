package kr.hs.dgsw.orange_market.controller

import kr.hs.dgsw.orange_market.domain.response.ResponseData
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.util.ResourceUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.lang.IllegalStateException
import java.lang.StringBuilder
import java.util.*

@RestController
@RequestMapping(value = ["/upload"])
class FileUploadController {

    @Value("\${spring.web.resources.static-locations}")
    private val path: String? = null

    @PostMapping(value = ["/image"])
    fun uploadImage(@RequestParam("file") file: MultipartFile): ResponseData<String> {
        if (!file.isEmpty) {
            val date = Date()
            val stringBuilder = StringBuilder()

            stringBuilder.append(date.time)
            stringBuilder.append(file.originalFilename)

            val resource = ResourceUtils.getFile(path!!)
            val temp = File("${resource.absolutePath}/${stringBuilder}")
            try {
                file.transferTo(temp)
                return ResponseData<String>(HttpStatus.OK, "업로드 성공", "$stringBuilder")
            } catch (e: IllegalStateException) {
                throw e
            } catch (e: IOException) {
                throw e
            }
        } else {
            throw HttpClientErrorException(HttpStatus.NOT_FOUND, "사진 없음")
        }
    }
}