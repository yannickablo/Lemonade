package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LemonadeApp(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LemonadeApp(modifier: Modifier = Modifier) {
    // État pour suivre l'étape actuelle (1 à 4)
    var currentStep by remember { mutableIntStateOf(1) }
    var squeezeCount by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Appelle la fonction qui gère l'affichage pour l'étape actuelle
        when (currentStep) {
            1 -> LemonadeImageAndText(
                textLabelResourceId = R.string.lemon_select,
                drawableResourceId = R.drawable.lemon_tree,
                contentDescriptionResourceId = R.string.lemon_tree,
                onImageClick = {
                    currentStep = 2
                    // Nombre aléatoire de pressions entre 2 et 4 (inclus)
                    squeezeCount = (2..4).random()
                }
            )
            2 -> LemonadeImageAndText(
                textLabelResourceId = R.string.lemon_squeeze,
                drawableResourceId = R.drawable.lemon_squeeze,
                contentDescriptionResourceId = R.string.lemon,
                onImageClick = {
                    // Diminuer le compteur de pressions
                    squeezeCount--
                    // Si le compteur atteint zéro, passer à l'étape 3
                    if (squeezeCount == 0) {
                        currentStep = 3
                    }
                }
            )
            3 -> LemonadeImageAndText(
                textLabelResourceId = R.string.lemon_drink,
                drawableResourceId = R.drawable.lemon_drink,
                contentDescriptionResourceId = R.string.glass_of_lemonade,
                onImageClick = {
                    // Passer à l'étape 4
                    currentStep = 4
                }
            )
            4 -> LemonadeImageAndText(
                textLabelResourceId = R.string.lemon_restart,
                drawableResourceId = R.drawable.lemon_restart,
                contentDescriptionResourceId = R.string.empty_glass,
                onImageClick = {
                    // Revenir à l'étape 1 pour recommencer
                    currentStep = 1
                }
            )
        }
    }
}

@Composable
fun LemonadeImageAndText(
    textLabelResourceId: Int,
    drawableResourceId: Int,
    contentDescriptionResourceId: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Le texte (libellé)
        Text(
            text = stringResource(textLabelResourceId),
            fontSize = 18.sp
        )
        // Espace entre le libellé et l'image
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = painterResource(drawableResourceId),
            contentDescription = stringResource(contentDescriptionResourceId),
            modifier = Modifier
                .clip(RoundedCornerShape(40.dp))
                .background(Color(0xFFC3ECC4))
                .border(width = 2.dp, color = Color(0xFF80A881),
                    shape = RoundedCornerShape(40.dp)
                )
                .clickable(onClick = onImageClick)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeAppPreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}