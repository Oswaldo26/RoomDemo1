package com.example.roomdemo

import android.app.Application
import android.app.appsearch.SearchResults
import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.roomdemo.ui.theme.RoomDemoTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val owner = LocalViewModelStoreOwner.current

                    owner?.let {
                        val viewModel:MainViewModel= viewModel(
                            it,
                            "MainViewModel",
                            MainViewModelFactory(
                                LocalContext.current.applicationContext as Application
                            )

                        )
                        ScreenSetup(viewModel)
                    }

                }

            }
        }
    }
}

@Composable
fun TitleRow(head1: String, head2:String, head3:String){
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
            .padding(5.dp)
    ){
        Text(head1, color = Color.White,
        modifier = Modifier
            .weight(0.1f))
        Text(head2,color = Color.White,
        modifier = Modifier
            .weight(0.2f))
        Text(head3, color = Color.White,
        modifier = Modifier
            .weight(0.2f))
    }
}

@Composable
fun AssitantRow(id: Int, name:String, quantityPay:Int){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ){
        Text(id.toString(), modifier = Modifier
            .weight(01f))
        Text(name, modifier = Modifier.weight(0.2f))
        Text(quantityPay.toString(), modifier = Modifier.weight(0.2f))
    }
}

@Composable
fun CustomTextField(
    title: String,
    textState: String,
    onTextChange:(String)->Unit,
    keyboardType: KeyboardType
){
    OutlinedTextField(
        value = textState,
        onValueChange ={onTextChange(it)},
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = true,
        label = { Text(title)},
        modifier = Modifier.padding(10.dp),
        textStyle = TextStyle(fontWeight = FontWeight.Bold,
        fontSize = 30.sp
        )

    )
}

@Composable
fun ScreenSetup(viewModel: MainViewModel){

    val allAssitant by viewModel.allAssitant.observeAsState(listOf())
    val searchResults by viewModel.searchResults.observeAsState(listOf())

    MainScreen(
        allAssitant = allAssitant,
        searchResults = searchResults,
        viewModel = viewModel
    )
}

@Composable
fun MainScreen(allAssitant: List<Assitant>,searchResults: List<Assitant>, viewModel: MainViewModel){

    var assitantName by remember { mutableStateOf("") }
    var assitantPay by remember { mutableStateOf("") }
    var searching by remember { mutableStateOf(false) }
    val onAssitantTextChange = { text : String -> assitantName = text }
    val onQuantityTextChange = { text : String -> assitantPay = text }

    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CustomTextField(title = "Assistant Name", textState = assitantName, onTextChange =onAssitantTextChange , keyboardType =KeyboardType.Text )

        CustomTextField(title = "Amount Pay", textState = assitantPay, onTextChange = onQuantityTextChange, keyboardType = KeyboardType.Number )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Button(onClick = {
                if(assitantPay.isNotEmpty()){
                    viewModel.insertAssitant(
                        Assitant(
                            assitantName,
                            assitantPay.toInt()
                        )
                    )
                    searching = false
                }
            }) {
                Text("Add")

            }
            /*
            Button(onClick = {
                searching = true
                viewModel.findAssitant(assitantName)
            }) {
                Text("Search")
            }
*/
            Button(onClick = {
                searching = false
                viewModel.deleteAssitant(assitantName)
            }) {
                Text("Delete")
            }

        }

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ){
            val list=if(searching) searchResults else allAssitant

            item{
                TitleRow(head1 = "ID", head2 = "Assistant" , head3 = "Amount Pay")
            }

            items(list){ assitant->
                AssitantRow(id=assitant.id, name=assitant.assitantName, quantityPay = assitant.quantityPay)

            }
        }
    }

}

class MainViewModelFactory(val application: Application):
        ViewModelProvider.Factory{
            override fun <T:ViewModel> create(modelClass:Class<T>):T{
                return MainViewModel(application)as T
            }
        }