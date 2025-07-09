package com.example.shift.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.shift.AppViewModel
import com.example.shift.R
import com.example.shift.User
import com.example.shift.ui.theme.SHIFTTheme
import com.example.shift.ui.theme.nunitoRegular

@Composable
fun FakeUserListScreen(mod: Modifier = Modifier) {
    Column(
        modifier = mod
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val data = mockUsers[0]

        LazyColumn(
            modifier = mod
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            item {
                MainCard(user = data, onUserClick = { })
            }
        }

        Button(onClick = { }) {
            Text(text = "Обновить")
        }
    }
}

@Composable
fun UserListScreen(
    mod: Modifier = Modifier,
    viewmodel: AppViewModel,
    onUserClick: (User) -> Unit
) {
    val users by viewmodel.users.collectAsState()
    val errorMessage by viewmodel.errorMessage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewmodel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = mod
                .statusBarsPadding()
                .padding(16.dp)
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn(
                modifier = mod
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                items(users) {
                    MainCard(user = it, onUserClick = onUserClick)
                }
            }
            Button(onClick = { viewmodel.refreshUsers() }) {
                Text(text = "Обновить")
            }
        }
    }

}

@Composable
fun MainCard(
    user: User,
    modifier: Modifier = Modifier,
    onUserClick: (User) -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onUserClick(user) },
        shape = RoundedCornerShape(
            topStart = 64.dp,
            bottomEnd = 64.dp,
            topEnd = 16.dp,
            bottomStart = 16.dp
        ),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(modifier = modifier.padding(16.dp), verticalAlignment = CenterVertically) {
            AsyncImage(
                model = user.picture.large.toString(),
                contentDescription = "profile picture",
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(64.dp)),
                placeholder = painterResource(id = R.drawable.loading),
                error = painterResource(id = R.drawable.error)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "${user.name.first} ${user.name.last}",
                    fontFamily = nunitoRegular,
                    fontSize = 28.sp
                )       // ФИО
                Text(
                    text = "${user.location.street.name}, ${user.location.city}",
                    fontFamily = nunitoRegular,
                    fontSize = 16.sp
                ) // Адрес
                Text(text = user.phone, fontFamily = nunitoRegular, fontSize = 10.sp) // Телефон
            }
        }
    }
}

@Preview(
    showSystemUi = true, showBackground = true
)
@Composable
private fun LightListPrev() {
    SHIFTTheme {
        Surface {
            FakeUserListScreen()
        }
    }

}

@Preview(
    showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun DarkListPrev() {
    SHIFTTheme {
        Surface {
            FakeUserListScreen()
        }
    }

}