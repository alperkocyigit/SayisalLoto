package com.example.sayisalloto

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sayisalloto.entity.Coupons
import com.example.sayisalloto.ui.theme.SayisalLotoKuponlarımSayfasiTheme
import com.example.sayisalloto.viewmodel.HomePageViewModel
import com.example.sayisalloto.viewmodelfactory.HomePageViewModelFactory
import com.google.gson.Gson


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SayisalLotoKuponlarımSayfasiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PageTransition()
                }
            }
        }
    }
}
@Composable
fun PageTransition(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "anasayfa"){
        composable("anasayfa"){
            HomePage(navController = navController)
        }
        composable("kupon_olusturma_sayfasi"){
            MyApp(navigationController = navController)
        }
        composable("kupon_olusturma_sayfasi_2"){
            RandomMyApp(navigationController = navController)
        }
        composable("kupon_guncelleme_sayfasi/{coupon}", arguments = listOf(
            navArgument("coupon"){ type = NavType.StringType
            })){
            val json = it.arguments?.getString("coupon") //coupon ismiyle gelen veriyi string çevirip alır.
            val nesne = Gson().fromJson(json,Coupons ::class.java)
            MyAppUpdate(navController, incomingCoupons = nesne)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun HomePage(navController: NavController) {

    val context = LocalContext.current
    val viewModel: HomePageViewModel = viewModel(
       factory = HomePageViewModelFactory(context.applicationContext as Application)
    )
    var searchActive by remember { mutableStateOf(false) }
    var tf by remember { mutableStateOf("") }
    val couponList = viewModel.couponList.observeAsState(listOf())
    LaunchedEffect(key1 = true){
        viewModel.coupons() // arayüzü hemen değiştirmek için yaptığımız işlem
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (searchActive){
                       TextField(value = tf,
                           onValueChange = {
                               tf = it
                               viewModel.search(it)
                               Log.e("Kupon Arama",it)},
                           label = {
                               Text(text = "Ara") },
                           colors = TextFieldDefaults.colors(
                               focusedContainerColor = Color.Transparent,
                               unfocusedContainerColor = Color.Transparent,
                               disabledContainerColor = Color.Transparent,
                               focusedLabelColor = Color.White,
                           )
                       )
                    }else{
                        Text(
                            text = "KAYITLI KUPONLARIM",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Left
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        )
                    }
                },
                actions = {
                    if (searchActive){
                        IconButton(onClick = {
                            searchActive = false
                            tf = ""
                        }) {
                            Icon(painter = painterResource(id = R.drawable.close_icon),
                                contentDescription = "",
                                tint = Color.Black)
                        }
                    }else{
                        IconButton(onClick = {
                            searchActive = true
                        }) {
                            Icon(painter = painterResource(id = R.drawable.search_icon),
                                contentDescription = "",
                                tint = Color.Black)
                        }
                    }
                }
            )
        },
        content = {
            LazyColumn(modifier = Modifier.padding(top = 62.dp)){
                items(
                    count = couponList.value!!.count(),
                    itemContent = {
                        val coupon = couponList.value!![it]
                        Card(modifier = Modifier
                            .padding(all = 6.dp)
                            .fillMaxWidth())
                        {
                            Row(modifier = Modifier.clickable {
                                val couponJson = Gson().toJson(coupon)
                                navController.navigate("kupon_guncelleme_sayfasi/${couponJson}")
                            }) {
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(all = 10.dp)
                                , verticalAlignment = Alignment.CenterVertically
                                , horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = couponList.value!![it].coupon_name)
                                    IconButton(onClick = {
                                        Toast.makeText(context, "'${coupon.coupon_name}' Kuponunuz Silindi.", Toast.LENGTH_LONG).show()
                                        viewModel.delete(coupon.coupon_id)
                                    }) {
                                        Icon(painter = painterResource(id = R.drawable.delete_icon),
                                            contentDescription = "",
                                            tint = Color.Gray
                                        )
                                    }

                                }
                            }
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            ExpandFloatingActionButton(
                onOption1Selected = {
                    navController.navigate("kupon_olusturma_sayfasi")
                },
                onOption2Selected = {
                    navController.navigate("kupon_olusturma_sayfasi_2")
                }
            )
        }
    )
}
@Composable
fun ExpandFloatingActionButton(
    onOption1Selected: () -> Unit,
    onOption2Selected: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        FloatingActionButton(
            onClick = {
                expanded = !expanded },
            shape = if (!expanded) CircleShape.copy(bottomEnd = CornerSize(2.dp)) else CircleShape,
            content = {
                Icon(
                    painter = painterResource(id = if (expanded) R.drawable.close_icon else R.drawable.add_icon),
                    contentDescription = "Expand button",
                    tint = Color.Unspecified,

                )
            },
            modifier = Modifier.padding(start = 180.dp, end = 10.dp, bottom = 10.dp)
        )

        if (expanded) {
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(start = 10.dp)
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally)

            ) {
                Button(
                    onClick = {
                        onOption1Selected()
                        expanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .padding(start = 15.dp)
                ) {
                    Text(text = "Yeni Kupon")
                }
                Button(
                    onClick = {
                        onOption2Selected()
                        expanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .padding(start = 15.dp)
                ) {
                    Text(text = "Şans Kuponu")
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SayisalLotoKuponlarımSayfasiTheme {

    }
}