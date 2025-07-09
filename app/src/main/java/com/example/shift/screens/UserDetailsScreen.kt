package com.example.shift.screens

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.composables.icons.lucide.Cake
import com.composables.icons.lucide.CalendarPlus2
import com.composables.icons.lucide.House
import com.composables.icons.lucide.IdCard
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Mail
import com.composables.icons.lucide.Mailbox
import com.composables.icons.lucide.Phone
import com.composables.icons.lucide.Smartphone
import com.composables.icons.lucide.User
import com.example.shift.AppViewModel
import com.example.shift.Coordinates
import com.example.shift.Dob
import com.example.shift.Id
import com.example.shift.Location
import com.example.shift.Name
import com.example.shift.Picture
import com.example.shift.R
import com.example.shift.Registered
import com.example.shift.Street
import com.example.shift.Timezone
import com.example.shift.User
import com.example.shift.ui.theme.SHIFTTheme
import java.time.LocalDate
import java.time.Period
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserDetailsScreen(
    mod: Modifier = Modifier,
    viewmodel: AppViewModel
) {
    val user = viewmodel.selectedUser!!


    Column(
        modifier = mod
            .statusBarsPadding()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MainCard(user = user)
        PersonalInfoCard(user = user)
        ContactInformationCard(user = user)
        AddressCard(user = user)
        AdditionalInfoCard(user = user)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FakeScreen(mod: Modifier = Modifier) {
    Column(
        modifier = mod
            .statusBarsPadding()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MainCard(user = mockUsers[0])
        PersonalInfoCard(user = mockUsers[0])
        ContactInformationCard(user = mockUsers[0])
        AddressCard(user = mockUsers[0])
        AdditionalInfoCard(user = mockUsers[0])
    }
}

@Composable
fun MainCard(mod: Modifier = Modifier, user: User) {
    Card(
        modifier = mod.fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = 48.dp,
            bottomEnd = 48.dp,
            topEnd = 16.dp,
            bottomStart = 16.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = mod
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//            Image(
//                painterResource(R.drawable.placeholder),
//                contentDescription = "preview image",
//                modifier = mod
//                    .size(96.dp)
//                    .clip(CircleShape)
//            )

            AsyncImage(
                model = user.picture.large.toString(),
                contentDescription = "profile picture",
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(64.dp)),
                placeholder = painterResource(id = R.drawable.loading),
                error = painterResource(id = R.drawable.error)
            )

            Column {
                Text(
                    text = "${user.name.first} ${user.name.last}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = user.name.title,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.displaySmall
                )
                TextWithIcon("${user.location.city}, ${user.location.state}")
            }
        }

    }
}

@Composable
fun TextWithIcon(msg: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            Icons.Filled.LocationOn,
            contentDescription = "location icon",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = msg,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Composable
fun TextWithIcon(mod: Modifier = Modifier, icon: ImageVector, title: String, data: String) {
    Row(
        modifier = mod.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$icon",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(24.dp)
        )
        Column {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = data,
                color = MaterialTheme.colorScheme.inverseSurface,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PersonalInfoCard(mod: Modifier = Modifier, user: User) {
    Card(
        modifier = mod.fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = 24.dp,
            bottomEnd = 24.dp,
            topEnd = 8.dp,
            bottomStart = 8.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(mod.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Личная информация",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            TextWithIcon(
                icon = Lucide.User,
                title = "Пол",
                data = user.gender
            )
            TextWithIcon(
                icon = Lucide.Cake,
                title = "Дата рождения",
                data = formatBirthDateWithAge(user.dob.date)
            )
            if (!user.id.value.isNullOrEmpty()) TextWithIcon(
                icon = Lucide.IdCard,
                title = "ID",
                data = "${user.id.name}: ${user.id.value}"
            )
        }

    }
}

@Composable
fun ContactInformationCard(mod: Modifier = Modifier, user: User) {
    val context = LocalContext.current

    Card(
        modifier = mod.fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = 24.dp,
            bottomEnd = 24.dp,
            topEnd = 8.dp,
            bottomStart = 8.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(
            mod
                .fillMaxWidth()
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(modifier = mod.fillMaxWidth(), text = "Контактная информация")
            TextWithIcon(
                icon = Lucide.Mail, title = "Email", data = user.email,
                mod = mod.clickable {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = "mailto:${user.email}".toUri()
                    }
                    context.startActivity(Intent.createChooser(intent, "Отправить email"))
                }
            )
            TextWithIcon(
                icon = Lucide.Phone, title = "Телефон", data = user.phone,
                mod = mod.clickable {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = "tel:${user.phone}".toUri()
                    }
                    context.startActivity(Intent.createChooser(intent, "Позвонить"))
                }
            )
            TextWithIcon(
                icon = Lucide.Smartphone, title = "Мобильный", data = user.cell,
                mod = mod.clickable {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = "tel:${user.cell}".toUri()
                    }
                    context.startActivity(Intent.createChooser(intent, "Позвонить"))
                })
        }

    }
}

@Composable
fun AddressCard(mod: Modifier = Modifier, user: User) {
    val context = LocalContext.current
    Card(
        modifier = mod.fillMaxWidth().clickable {
            val address = "${user.location.street.name} ${user.location.street.number}, ${user.location.city}, ${user.location.country}"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = "geo:0,0?q=$address".toUri()
            }
            context.startActivity(Intent.createChooser(intent, "Открыть карту"))
        },
        shape = RoundedCornerShape(
            topStart = 24.dp,
            bottomEnd = 24.dp,
            topEnd = 8.dp,
            bottomStart = 8.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(mod.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Адрес")
            TextWithIcon(
                icon = Lucide.House,
                title = "Улица",
                data = "${user.location.street.name}, ${user.location.street.number}"
            )
            TextWithIcon(
                icon = ImageVector.vectorResource(R.drawable.city),
                title = "Город/Штат/Страна",
                data = "${user.location.city} / ${user.location.state} / ${user.location.country}"
            )
            TextWithIcon(
                icon = Lucide.Mailbox,
                title = "Почтовый индекс",
                data = user.location.postcode
            )
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdditionalInfoCard(mod: Modifier = Modifier, user: User) {
    Card(
        modifier = mod.fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = 24.dp,
            bottomEnd = 24.dp,
            topEnd = 8.dp,
            bottomStart = 8.dp
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(mod.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Контактная информация")
            TextWithIcon(icon = Lucide.User, title = "Имя пользователя", data = user.email)
            TextWithIcon(
                icon = Lucide.CalendarPlus2,
                title = "Зарегистрирован",
                data = formatBirthDateWithAge(user.registered.date)
            )
            TextWithIcon(
                icon = ImageVector.vectorResource(R.drawable.planet),
                title = "Национальность",
                data = user.nat
            )
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatBirthDateWithAge(dateString: String): String {
    val zonedDateTime = ZonedDateTime.parse(dateString)
    val birthDate = zonedDateTime.toLocalDate()
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
    val formattedDate = birthDate.format(formatter)
    val age = Period.between(birthDate, LocalDate.now()).years
    return "$formattedDate ($age лет)"
}

val mockUsers = listOf(
    User(
        gender = "male",
        name = Name(
            title = "Mr",
            first = "Ivan",
            last = "Petrov"
        ),
        location = Location(
            street = Street(
                number = 123,
                name = "Lenina"
            ),
            city = "Moscow",
            state = "Moscow",
            country = "Russia",
            postcode = "101000",
            coordinates = Coordinates(
                latitude = "55.7558",
                longitude = "37.6173"
            ),
            timezone = Timezone(
                offset = "+3:00",
                description = "Moscow Time"
            )
        ),
        email = "ivan.petrov@example.com",
        dob = Dob(
            date = "1990-05-15T10:00:00Z",
            age = 35
        ),
        registered = Registered(
            date = "2015-09-20T12:00:00Z",
            age = 9
        ),
        phone = "+7-495-1234567",
        cell = "+7-916-1234567",
        id = Id("SSN", "405-88-3636"),
        picture = Picture(
            large = "https://randomuser.me/api/portraits/men/1.jpg",
            medium = "https://randomuser.me/api/portraits/med/men/1.jpg",
            thumbnail = "https://randomuser.me/api/portraits/thumb/men/1.jpg"
        ),
        nat = "RU"
    )
)

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ScreenPrev1() {
    SHIFTTheme {
        Surface {
            FakeScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DarkScreenPrev1() {
    SHIFTTheme {
        Surface {
            FakeScreen()
        }
    }
}