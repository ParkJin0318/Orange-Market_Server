package kr.hs.dgsw.orange_market.controller

import kr.hs.dgsw.orange_market.domain.request.ProductRequest
import kr.hs.dgsw.orange_market.domain.response.ProductData
import kr.hs.dgsw.orange_market.domain.response.Response
import kr.hs.dgsw.orange_market.domain.response.ResponseData
import kr.hs.dgsw.orange_market.service.product.ProductServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException

@RestController
@RequestMapping(value = ["/product"])
class ProductController {

    @Autowired
    private lateinit var productService: ProductServiceImpl

    @GetMapping(value = [""])
    fun getAllProducts(@RequestParam("city") city: String): ResponseData<List<ProductData>> {
        try {
            val data = productService.getAllProduct(city)
            return ResponseData(HttpStatus.OK, "조회 성공", data)

        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류")
        }
    }

    @GetMapping(value = ["{idx}"])
    fun getProduct(@PathVariable("idx") idx: Int): ResponseData<ProductData> {
        try {
            val data = productService.getProduct(idx)
            return ResponseData(HttpStatus.OK, "", data)
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류")
        }
    }

    @PostMapping(value = [""])
    fun saveProduct(@RequestBody productRequest: ProductRequest): Response {
        try {
            val idx = productService.saveProduct(productRequest)
            productService.saveProductImage(idx, productRequest.imageList!!)
            return Response(HttpStatus.OK, "추가 성공")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류")
        }
    }
}