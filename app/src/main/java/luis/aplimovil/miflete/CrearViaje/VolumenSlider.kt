package luis.aplimovil.miflete.CrearViaje

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas

import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.times


@Composable
fun VolumenCuboSlider(
    volumen: Float,
    onVolumenChange: (Float) -> Unit,
    min: Float = 0f,
    max: Float = 100f,
    azul: Color = Color(0xFF072A53),
    naranja: Color = Color(0xFFF47C20)
) {
    // El tamaño del cubo crecerá entre 60dp y 180dp por ejemplo
    val sizeAnim by animateDpAsState(
        targetValue = (60.dp + (volumen / max) * 120.dp),
        animationSpec = tween(durationMillis = 500), label = ""
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // El cubo animado
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CuboIsometrico(
                size = sizeAnim,
                colorPrincipal = azul,
                colorLateral = azul.copy(alpha = 0.8f),
                colorSuperior = naranja
            )
        }
        // Slider
        Slider(
            value = volumen,
            onValueChange = { onVolumenChange(it) },
            valueRange = min..max,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        Text(
            text = "Volumen: ${volumen.toInt()} m³",
            fontSize = 16.sp,
            color = azul
        )
    }
}

@Composable
fun CuboIsometrico(
    size: androidx.compose.ui.unit.Dp,
    colorPrincipal: Color,
    colorLateral: Color,
    colorSuperior: Color,
) {
    // El cubo isométrico se dibuja con 3 Path: frente, lado, arriba.
    Canvas(
        modifier = Modifier
            .size(size)
    ) {
        val w = size.toPx()
        val h = size.toPx()
        val depth = 0.5f * w

        // Coordenadas para isométrico
        val front = Path().apply {
            moveTo(w * 0.25f, h * 0.25f)
            lineTo(w * 0.75f, h * 0.25f)
            lineTo(w * 0.75f, h * 0.75f)
            lineTo(w * 0.25f, h * 0.75f)
            close()
        }
        val side = Path().apply {
            moveTo(w * 0.75f, h * 0.25f)
            lineTo(w * 0.75f + depth * 0.4f, h * 0.25f - depth * 0.2f)
            lineTo(w * 0.75f + depth * 0.4f, h * 0.75f - depth * 0.2f)
            lineTo(w * 0.75f, h * 0.75f)
            close()
        }
        val top = Path().apply {
            moveTo(w * 0.25f, h * 0.25f)
            lineTo(w * 0.75f, h * 0.25f)
            lineTo(w * 0.75f + depth * 0.4f, h * 0.25f - depth * 0.2f)
            lineTo(w * 0.25f + depth * 0.4f, h * 0.25f - depth * 0.2f)
            close()
        }

        drawPath(front, colorPrincipal, style = Fill)
        drawPath(side, colorLateral, style = Fill)
        drawPath(top, colorSuperior, style = Fill)
    }
}