package com.haiphong.mentalhealthapp.view.screens.customer.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.haiphong.mentalhealthapp.view.screens.customer.intro.dates
import com.haiphong.mentalhealthapp.view.screens.customer.intro.genders
import com.haiphong.mentalhealthapp.view.screens.customer.intro.months
import com.haiphong.mentalhealthapp.view.screens.customer.intro.years
import com.haiphong.mentalhealthapp.view.composables.Dropdown
import com.haiphong.mentalhealthapp.view.composables.StandardTextField
import com.haiphong.mentalhealthapp.viewmodel.profile.CustomerEditProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerEditProfileScreen(
    scrollState: ScrollState = rememberScrollState(),
    viewModel: CustomerEditProfileViewModel = viewModel(),
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    val profileState by viewModel.profileState.collectAsState()

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            imageUri = uri
            Log.d("Get Image", uri.toString())
        }
    )

    Column(
        modifier = Modifier
            .padding(15.dp)
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.default_avatar),
//            contentDescription = null,
//            modifier = Modifier
//                .clip(
//                    CircleShape
//                )
//                .size(200.dp)
//        )
        Surface(
            shape = CircleShape, modifier = Modifier
                .size(150.dp)
        ) {
            AsyncImage(modifier = Modifier.clickable {
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }, model = imageUri, contentDescription = null)
        }
        StandardTextField(
            value = profileState.name,
            label = "Name",
            onValueChange = viewModel::onNameChange,
            isInvalidated = false
        )
        Text("Gender:")
        Row() {
            genders.forEach { text ->
                RadioButton(
                    selected = text == profileState.gender,
                    onClick = { viewModel.onGenderChange(text) })
                Text(text = text)
            }
        }
        Text("Date of birth:")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Dropdown(
                items = dates,
                item = profileState.date,
                onValueChange = viewModel::onDateChange
            )
            Dropdown(
                items = months,
                item = profileState.month,
                onValueChange = viewModel::onMonthChange
            )
            Dropdown(
                items = years,
                item = profileState.year,
                onValueChange = viewModel::onYearChange
            )
        }
        Text("Biography:")
        OutlinedTextField(
            value = profileState.bio,
            onValueChange = viewModel::onBioChange,
            label = { Text("Biography") },
            placeholder = { Text("Introduce about yourself...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            viewModel.updateUser(onSave = onSave)
        }) {
            Text("Save")
        }
        Button(onClick = { onCancel() }) {
            Text("Cancel")
        }
    }

}