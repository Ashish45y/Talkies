package dev.ashish.talkies.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import dev.ashish.talkies.models.Urls
import dev.ashish.talkies.models.User

@ProvidedTypeConverter
class Converters {

    @TypeConverter
    fun fromUrls(urls: Urls): String {
        return urls.regular
    }

    @TypeConverter
    fun toUrls(urls: String): Urls {
        return Urls(urls)
    }

    @TypeConverter
    fun fromUser(user: User): String {
        return user.username
    }

    @TypeConverter
    fun toUser(user: String): User {
        return User(user)
    }


}