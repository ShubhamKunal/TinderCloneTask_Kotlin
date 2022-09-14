package com.example.task3

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.task3.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tinderView: TinderView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tinderView =ViewModelProvider(this)[TinderView::class.java]
        tinderView.modelStream.observe(this,{bindCard(it)})

        binding.motionLayout.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                when(currentId) {
                    R.id.offScreenPass, R.id.offScreenLike, R.id.offScreenSuperLike -> {
                        tinderView.swipe()
                    }
                }
                super.onTransitionCompleted(motionLayout, currentId)
            }
        })

    }

    private fun bindCard(model: Tinder) {
        binding.topCard.setContent{
            ProfileCard(model.top.profilePhoto, model.top.name, model.top.bio)
        }
        binding.bottomCard.setContent{
            ProfileCard(model.bottom.profilePhoto, model.bottom.name, model.bottom.bio)
        }
    }
    @Composable
    fun ProfileCard(profilePhoto:Int, name:String, bio:String){
        Card(
            modifier = Modifier.fillMaxSize(),
            elevation = 4.dp,
            shape = RoundedCornerShape(20.dp),
        ){
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = profilePhoto),
                contentDescription = "Profile photo",
                contentScale = ContentScale.Crop,
            )
            Column(
                verticalArrangement = Arrangement.Bottom,
            ) {
                Column(modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0x00000000),
                                Color(0xFF000000)
                            )
                        )
                    )
                    .fillMaxWidth()
                    .padding(16.dp)
                ) {
                    Text(
                        name,
                        fontWeight = FontWeight.W700,
                        color = Color(0xFFFFFFFF),
                        fontSize = 26.sp,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        bio,
                        color = Color(0xFFFFFFFF),
                        lineHeight = 22.sp,
                        fontSize = 18.sp,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircleButton(R.drawable.ic_dislike, Color(0xFFD84938), "Dislike", 75.dp
                        ) { binding.motionLayout.transitionToState(R.id.pass) }
                        CircleButton(R.drawable.ic_super_like, Color(0xFF703EB0), "Super Like",115.dp
                        ) { binding.motionLayout.transitionToState(R.id.superLike) }
                        CircleButton(R.drawable.ic_love, Color(0xFF9AEBA2), "Love", 75.dp
                        ) { binding.motionLayout.transitionToState(R.id.like) }
                    }
                }
            }
        }
    }
    @Composable
    fun CircleButton(
        icon: Int,
        color: Color,
        description: String,
        size: Dp = 40.dp,
        onClick: () -> Unit = {}
    ){
        OutlinedButton(
            onClick = onClick,
            border = BorderStroke(1.dp, color),
            shape = RoundedCornerShape(50),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .width(size)
                .height(size),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Color(0x00000000)
            )
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = description,
                Modifier.width(size / 2).height(size / 2),
            )
        }
    }

}