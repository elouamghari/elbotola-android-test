package com.elouamghari.test.elbotola.extensions

import com.elouamghari.test.elbotola.api.models.Contestant
import com.elouamghari.test.elbotola.managers.FollowedTeamsManager

fun Contestant.follow(){
    FollowedTeamsManager.follow(this)
}

fun Contestant.unfollow(){
    FollowedTeamsManager.unfollow(this)
}

fun Contestant.isFollowed(): Boolean{
    return FollowedTeamsManager.isFollowed(this)
}