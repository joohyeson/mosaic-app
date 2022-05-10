package com.nekara.mosaicapp.mlkit.vision.text_recognition

import android.graphics.*
import com.google.mlkit.vision.text.Text
import com.nekara.mosaicapp.camerax.GraphicOverlay

class TextRecognitionGraphic(
    overlay: GraphicOverlay,
    private val element: Text.TextBlock,
    private val imageRect: Rect
) : GraphicOverlay.Graphic(overlay) {

    private val rectPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        alpha = 255
    }

    private val textPaint = Paint().apply {
        color = TEXT_COLOR
        textSize = TEXT_SIZE
    }

    override fun draw(canvas: Canvas?) {
//        rectPaint.setMaskFilter(BlurMaskFilter(8F, BlurMaskFilter.Blur.NORMAL))
        element.boundingBox?.let { box ->
            val rect = calculateRect(imageRect.height().toFloat(), imageRect.width().toFloat(), box)
            canvas?.drawRoundRect(rect, ROUND_RECT_CORNER, ROUND_RECT_CORNER, rectPaint)
            canvas?.drawText(element.text, rect.left, rect.bottom, textPaint) //화면에 텍스트 띄움
        }
    }

    companion object {
        private const val TEXT_COLOR = Color.WHITE
        private const val TEXT_SIZE = 50.1f
        private const val ROUND_RECT_CORNER = 8F
    }
}