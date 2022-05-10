package com.nekara.mosaicapp.mlkit.vision.text_recognition

import android.content.Context
import android.graphics.Rect
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions

import com.nekara.mosaicapp.camerax.BaseImageAnalyzer
import com.nekara.mosaicapp.camerax.GraphicOverlay
import java.io.IOException

class TextRecognitionProcessor(private val context: Context, private val view: GraphicOverlay) : BaseImageAnalyzer<Text>() {
    //private val textRecognizerOptions: TextRecognizerOptions = KoreanTextRecognizerOptions.Builder().build()
    private val recognizer: TextRecognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
    private val filename = "test.txt"

    override val graphicOverlay: GraphicOverlay
        get() = view

    override fun detectInImage(image: InputImage): Task<Text> {
        return recognizer.process(image)
    }

    override fun stop() {
        try {
            recognizer.close()
        } catch (e: IOException) {
            Log.e(TAG, "Exception thrown while trying to close Text Recognition: $e")
        }
    }

    override fun onSuccess(
        results: Text,
        graphicOverlay: GraphicOverlay,
        rect: Rect
    ) {
        graphicOverlay.clear()
        results.textBlocks.forEach {
            val textGraphic = TextRecognitionGraphic(graphicOverlay, it, rect)
            graphicOverlay.add(textGraphic)
        }
        graphicOverlay.postInvalidate()

        // 텍스트 저장하기
        val fileContents = results.text

        try {
            context.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(fileContents.toByteArray())
            }
        } catch (e: IOException) {
            Log.d(TAG, "error!")
        }

    }

    override fun onFailure(e: Exception) {
        Log.w(TAG, "Text Recognition failed.$e")
    }

    companion object {
        private const val TAG = "TextProcessor"
    }

}