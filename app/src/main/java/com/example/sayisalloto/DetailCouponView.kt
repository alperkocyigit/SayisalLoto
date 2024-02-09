package com.example.sayisalloto

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sayisalloto.entity.Coupons
import com.example.sayisalloto.extension.LimitedLengthVisualTransformation
import com.example.sayisalloto.viewmodel.DetailCouponViewModel
import com.example.sayisalloto.viewmodelfactory.DetailCouponViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyAppUpdate(navigationController: NavController,incomingCoupons: Coupons) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedColumns by remember { mutableStateOf(incomingCoupons.coupon_colons) }
    var boxHeight by remember { mutableStateOf(((selectedColumns.size * 60)).dp) }
    var couponName by remember { mutableStateOf(incomingCoupons.coupon_name) } // Kupon adı için değişken

    val context = LocalContext.current
    val viewModel: DetailCouponViewModel = viewModel(
        factory = DetailCouponViewModelFactory(context.applicationContext as Application)
    )

    LaunchedEffect(key1 = true){
        couponName = incomingCoupons.coupon_name
        selectedColumns = incomingCoupons.coupon_colons
    } // yaşam döngüsü methodu sayfa açılır açılmaz çalışır ve arayüzü günceller.



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        BackButton(navigationController = navigationController )
                        Spacer(modifier = Modifier)
                        Text(text = "KUPON GÜNCELLEME", modifier = Modifier.padding(top = 9.dp))
                    }

                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 35.dp)
                    .padding(bottom = 50.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                // Dikdörtgen boş bir kart oluşturuldu.
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        //.height(boxHeight)
                        .border(width = 1.dp, color = Color.Black),
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                        color = Color.White
                    ) {
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(color = Color.Gray)
                                    .height(60.dp))
                            {
                                Text("Seçilen Kolonlar", fontSize = 20.sp, textAlign = TextAlign.Center ,modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                                )
                            }
                            Column(modifier = Modifier
                                .background(color = Color.White)
                                .padding(start = 7.dp))
                            {
                                selectedColumns.forEachIndexed { index, column ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Kolon ${index + 1}:")
                                        Spacer(modifier = Modifier.width(5.dp))
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                                        ) {
                                            column.forEach { number ->
                                                Box(modifier = Modifier
                                                    .clip(shape = CircleShape)
                                                    .background(color = Color.Black)
                                                    .width(28.dp)
                                                    .height(28.dp)){
                                                    Text(
                                                        text = number.toString(),
                                                        color = Color.White,
                                                        modifier = Modifier
                                                            .padding(2.dp)
                                                            .padding(start = 3.dp)

                                                    )
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Button(onClick = {
                                            selectedColumns = selectedColumns.toMutableList().also { it.removeAt(index) }
                                            boxHeight -= 68.dp
                                        }) {
                                            Text("Sil")
                                        }
                                    }
                                    Divider(modifier = Modifier
                                        .padding(vertical = 2.dp)
                                        .background(color = Color.Black))
                                }
                            }

                        }
                    }
                }
                Button(onClick = { showDialog = true }) {
                    Text(text = "Kolon Ekle")
                }
                if (showDialog) {
                    NumberSheet(
                        onDismiss = { colon ->
                            selectedColumns += mutableListOf(colon.toList()) //number sheet sayfasında kaydettiğimiz set yapıyı burada listeye dönüştürüp ekledik.
                            boxHeight += 68.dp
                        },
                        onDismissRequest = { showDialog = false }
                    )
                }
                OutlinedTextField( modifier = Modifier.width(325.dp),
                    value = couponName,
                    maxLines = 1,
                    onValueChange = { if(it.length <= 20){
                        couponName = it
                    } },
                    visualTransformation = LimitedLengthVisualTransformation(20),
                    label = { Text("Kupon Adı") },
                    placeholder = {Text(text = "Maksimum 20 karakter", color = Color.LightGray)}
                )

            }
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RectangleShape,
                onClick = {
                    Toast.makeText(context, " '${couponName}' Kuponunuz Güncellendi", Toast.LENGTH_LONG).show()
                    viewModel.update(incomingCoupons.coupon_id,couponName,selectedColumns)
                },
                enabled = selectedColumns.isNotEmpty() && couponName.isNotEmpty()
            ) {
                Text(text = "Kuponu Güncelle")
            }
        }


    )
}
@Preview
@Composable
fun PreviewUpdateApp() {
    MaterialTheme {

    }
}