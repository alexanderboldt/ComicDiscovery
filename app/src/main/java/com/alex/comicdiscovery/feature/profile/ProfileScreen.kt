package com.alex.comicdiscovery.feature.profile

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.alex.comicdiscovery.R
import com.alex.comicdiscovery.ui.theme.*
import com.alex.comicdiscovery.util.getColor
import org.koin.androidx.compose.getViewModel
import java.io.File

@Composable
fun ProfileScreen() {
    val viewModel: ProfileViewModel = getViewModel()

    Column(
        modifier = Modifier
            .background(getColor(BrightGray, DarkCharcoal))
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        val textStyle = TextStyle(
            color = getColor(DarkElectricBlue, BrightGray),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = stringResource(R.string.profile_title),
            style = textStyle,
            modifier = Modifier.padding(16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            if (viewModel.avatar == null) {
                Image(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    colorFilter = ColorFilter.tint(DarkElectricBlue)
                )
            } else {
                Image(
                    painter = rememberImagePainter(viewModel.avatar),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                        .clip(CircleShape)
                        .border(2.dp, DarkCharcoal, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            val context = LocalContext.current

            val launcherActivityResult = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                result.data?.data?.let { uri ->
                    val cacheFile = context.cacheDir.resolve("cache")
                    context.contentResolver.openInputStream(uri)?.copyTo(cacheFile.outputStream())
                    viewModel.onAvatarSelected(cacheFile)
                }
            }

            IconButton(
                onClick = {
                    Intent(Intent.ACTION_GET_CONTENT)
                        .apply {
                            type = "image/*"
                            addCategory(Intent.CATEGORY_OPENABLE)
                        }.apply { launcherActivityResult.launch(this) }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(48.dp)
                    .clip(CircleShape)
                    .background(UltramarineBlue)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = null,
                    tint = BrightGray
                )
            }
        }
    }
}