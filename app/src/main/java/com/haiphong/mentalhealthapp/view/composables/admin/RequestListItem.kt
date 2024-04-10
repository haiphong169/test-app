package com.haiphong.mentalhealthapp.view.composables.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.haiphong.mentalhealthapp.R
import com.haiphong.mentalhealthapp.model.Request

@Composable
fun RequestListItem(request: Request, toRequest: (String) -> Unit) {
    Card(modifier = Modifier
        .padding(16.dp)
        .clickable { toRequest(request.requestId) }) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.default_avatar),
                contentDescription = "avatar",
                modifier = Modifier.size(200.dp)
            )
            Column {
                Text(text = request.specialist.name)
                Text(text = request.specialist.workplace)
                request.specialist.credentials.forEach {
                    Text(text = it)
                }
            }
        }
    }
}