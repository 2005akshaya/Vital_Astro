package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GeometricGoldMetallic
import com.example.ui.theme.GeometricGoldPrimary
import com.example.ui.theme.GeometricTextPrimary

@Composable
fun TamilSacredDivider(
  modifier: Modifier = Modifier,
  color: Color = GeometricGoldMetallic.copy(alpha = 0.35f)
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    Canvas(modifier = Modifier.weight(1f).height(1.dp)) {
      drawLine(
        color = color,
        start = Offset(0f, 0f),
        end = Offset(size.width, 0f),
        strokeWidth = 1.dp.toPx()
      )
    }

    Text(
      text = " ✧ ॐ ✧ ",
      style = MaterialTheme.typography.labelMedium.copy(
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = GeometricGoldPrimary
      ),
      modifier = Modifier.padding(horizontal = 8.dp)
    )

    Canvas(modifier = Modifier.weight(1f).height(1.dp)) {
      drawLine(
        color = color,
        start = Offset(0f, 0f),
        end = Offset(size.width, 0f),
        strokeWidth = 1.dp.toPx()
      )
    }
  }
}

@Composable
fun TraditionalMotifBadge(
  text: String,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Canvas(modifier = Modifier.size(8.dp)) {
      val path = Path().apply {
        moveTo(size.width / 2f, 0f)
        lineTo(size.width, size.height / 2f)
        lineTo(size.width / 2f, size.height)
        lineTo(0f, size.height / 2f)
        close()
      }
      drawPath(path = path, color = GeometricGoldPrimary, style = Stroke(width = 1.5.dp.toPx()))
    }
    Spacer(modifier = Modifier.width(6.dp))
    Text(
      text = text,
      style = MaterialTheme.typography.labelSmall.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 10.5.sp,
        letterSpacing = 0.5.sp,
        color = GeometricGoldPrimary
      )
    )
  }
}
