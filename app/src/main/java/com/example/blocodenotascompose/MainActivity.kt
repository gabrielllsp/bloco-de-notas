package com.example.blocodenotascompose

import BLACK
import BlocoDeNotasComposeTheme
import GOLD
import WHITE
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blocodenotascompose.datastore.StoreAnnotation
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlocoDeNotasComposeTheme {
                BlocoDeNotasComponentes()
            }

        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BlocoDeNotasComponentes() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val storeAnotacao = StoreAnnotation(context)
    val anotacaoSalva = storeAnotacao.getAnotacao.collectAsState(initial = "")


    var anotacao by remember {
        mutableStateOf("")
    }

    anotacao = anotacaoSalva.value

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = GOLD
            ) {
                Text(
                    text = "Bloco de Notas",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = BLACK
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        storeAnotacao.salvarAnotacao(anotacao)
                        Toast.makeText(context, "Anotação salva com sucesso!", Toast.LENGTH_LONG)
                            .show()
                    }
                },
                backgroundColor = GOLD,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp
                )
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_save),
                    contentDescription = "Icone de salvar anotação"
                )
            }
        }
    ) {
        Column() {
            TextField(
                value = anotacao,
                onValueChange = {
                    anotacao = it
                },
                label = {
                    Text(text = "Digite sua anotação")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = WHITE,
                    cursorColor = GOLD,
                    focusedLabelColor = WHITE
                )
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun BlocoDeNotasPreview() {
    BlocoDeNotasComposeTheme {
        BlocoDeNotasComponentes()
    }

}

