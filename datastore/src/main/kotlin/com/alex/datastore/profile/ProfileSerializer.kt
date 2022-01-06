package com.alex.datastore.profile

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.alex.datastore.Profile
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal object ProfileSerializer : Serializer<Profile> {
    override val defaultValue: Profile = Profile.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Profile {
        try {
            return Profile.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(t: Profile, output: OutputStream) = t.writeTo(output)
}