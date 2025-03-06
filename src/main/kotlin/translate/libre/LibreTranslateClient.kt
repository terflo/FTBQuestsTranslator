package translate.libre

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ru.terflo.translate.libre.dto.LibreTranslateRequestDto
import ru.terflo.translate.libre.dto.LibreTranslateResponseDto
import translate.TranslateClient
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers
import java.nio.charset.StandardCharsets
import kotlin.system.exitProcess

class LibreTranslateClient(
    private val apiUrl: URI = URI("http://192.168.0.199:5000/translate"),
    private val apiKey: String? = null,
    private val objectMapper: ObjectMapper = ObjectMapper(),
    private val httpClient: HttpClient = HttpClient.newHttpClient()
) : TranslateClient {

    override fun translate(text: String, sourceLang: String, targetLang: String): String {
        val request = buildRequest(text, sourceLang, targetLang)
        val response = sendRequest(request)
        return getTranslatedText(response)
    }

    private fun buildRequest(text: String, sourceLang: String, targetLang: String): HttpRequest {
        val dto = LibreTranslateRequestDto(q = text, source = sourceLang, target = targetLang, api_key = apiKey)
        val json = objectMapper.writeValueAsString(dto)
        return HttpRequest.newBuilder()
            .uri(apiUrl)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build()
    }

    private fun getTranslatedText(response: HttpResponse<String>): String {
        val body = response.body()
        val responseDto = objectMapper.readValue<LibreTranslateResponseDto>(body)

        if (responseDto.error != null) {
            println("\nError: \"${responseDto.error}\"")
            exitProcess(-1)
        }

        return responseDto.translatedText
    }

    private fun sendRequest(request: HttpRequest): HttpResponse<String> =
        httpClient.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8))

}