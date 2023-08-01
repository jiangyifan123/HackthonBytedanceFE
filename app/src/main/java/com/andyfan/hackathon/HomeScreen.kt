package com.andyfan.hackathon

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 30.dp, start = 10.dp, end = 10.dp, bottom = 30.dp)
            .verticalScroll(rememberScrollState()),
        shape = RoundedCornerShape(10.dp)
    ) {
        ConstraintLayout{
            val (title, messageInput, nextButton) = createRefs()

            var textState by remember {
                mutableStateOf("")
            }

            val guideLine = createGuidelineFromTop(0.1f)

            Text(text = "please input the message",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(guideLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(messageInput.top)
                }
            )
            TextField(
                value = textState,
                onValueChange = { textState = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(start = 10.dp, end = 10.dp)
                    .constrainAs(messageInput) {
                        top.linkTo(title.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(nextButton.bottom)
                    },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                ),
                label = { Text(text = "message:")},
                shape = RoundedCornerShape(10.dp)
            )

            Button(
                onClick = { textState = "click" },
                modifier = Modifier.constrainAs(nextButton) {
                    top.linkTo(messageInput.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Text(text = "Next")
            }
        }
    }
}