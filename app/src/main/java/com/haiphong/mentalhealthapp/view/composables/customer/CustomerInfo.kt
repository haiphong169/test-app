package com.haiphong.mentalhealthapp.view.composables.customer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haiphong.mentalhealthapp.R
import com.haiphong.mentalhealthapp.model.User

@Composable
fun CustomerInfo(customer: User) {
    Card(modifier = Modifier.padding(6.dp)) {
        Column(modifier = Modifier.padding(10.dp).height(500.dp)) {
            Image(
                painter = painterResource(id = R.drawable.default_avatar),
                contentDescription = null,
                modifier = Modifier
                    .clip(
                        CircleShape
                    )
                    .size(200.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop
            )
            Text(
                text = customer.name,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Gender: ${customer.gender}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Date of birth: ${customer.date}/${customer.month}/${customer.year}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Bio:", style = MaterialTheme.typography.titleLarge)
            Text(text = customer.bio, fontSize = 18.sp)
        }
    }
}