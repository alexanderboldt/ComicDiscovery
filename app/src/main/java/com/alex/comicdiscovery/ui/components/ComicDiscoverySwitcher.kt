package com.alex.comicdiscovery.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.alex.comicdiscovery.ui.theme.*
import com.alex.comicdiscovery.ui.theme.DarkElectricBlue
import com.alex.comicdiscovery.util.getColor
import java.lang.IllegalArgumentException

@Composable
fun ComicDiscoverySwitcher(modifier: Modifier = Modifier, options: List<String>, selected: Int = 0, onClick: (Int) -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(1.dp, getColor(UltramarineBlue, DarkElectricBlue), RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .padding(0.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically) {

        if (options.size < 2) throw IllegalArgumentException("Number of options must be at least 2")

        options.forEachIndexed { index, text ->

            val backgroundColor = when (selected) {
                index -> getColor(UltramarineBlue, DarkElectricBlue)
                else -> getColor(BrightGray, DarkCharcoal)
            }

            val contentColor = when (selected) {
                index -> getColor(BrightGray, BrightGray)
                else -> getColor(UltramarineBlue, BrightGray)
            }

            Button(
                onClick = { onClick(index) },
                colors = ButtonDefaults.buttonColors(backgroundColor, contentColor),
                shape = RoundedCornerShape(0.dp),
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                modifier = Modifier.weight(1f).fillMaxHeight()) {
                Text(text)
            }

            if (index != options.lastIndex) {
                Spacer(modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(getColor(UltramarineBlue, DarkElectricBlue)))
            }
        }
    }
}