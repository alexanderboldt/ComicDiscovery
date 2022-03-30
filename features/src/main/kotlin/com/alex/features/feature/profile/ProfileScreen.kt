package com.alex.features.feature.profile

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.alex.features.R
import com.alex.features.ui.theme.*
import com.alex.features.util.getColor
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.getViewModel

@Destination(route = "ProfileScreen")
@ExperimentalAnimationApi
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
            AnimatedContent(
                targetState = viewModel.state.avatar,
                transitionSpec = {
                    fadeIn(animationSpec = tween(500)) with fadeOut(animationSpec = tween(500))
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .clip(CircleShape)
                    .border(2.dp, getColor(DarkCharcoal, BrightGray), CircleShape)
                    .background(DarkElectricBlue)
            ) { avatar ->
                if (avatar == null) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(BrightGray)
                    )
                } else {
                    Image(
                        painter = rememberImagePainter(avatar),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            val context = LocalContext.current

            val launcherActivityResult = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                result.data?.data?.let { uri ->
                    val cacheFile = context.cacheDir.resolve("avatar")
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
                    .padding(44.dp)
                    .clip(CircleShape)
                    .background(CoralRed)
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