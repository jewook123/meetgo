package com.meetgo.app.data.model

data class UserProfile(
    val id: String,
    val name: String,
    val age: Int,
    val bio: String,
    val imageUrl: String,
    val interests: List<String> = emptyList(),
)

data class MatchPreview(
    val id: String,
    val name: String,
    val imageUrl: String,
    val lastMessage: String,
    val isUnread: Boolean = false,
)

data class ChatMessage(
    val id: String,
    val text: String,
    val isMine: Boolean,
)

object SampleData {
    val discoverProfiles = listOf(
        UserProfile(
            id = "1",
            name = "지은",
            age = 27,
            bio = "주말엔 등산, 평일엔 카페 투어 좋아해요.",
            imageUrl = "https://picsum.photos/seed/meetgo1/600/800",
            interests = listOf("등산", "카페", "영화"),
        ),
        UserProfile(
            id = "2",
            name = "민준",
            age = 29,
            bio = "러닝 크루 운영 중입니다. 같이 뛰실 분!",
            imageUrl = "https://picsum.photos/seed/meetgo2/600/800",
            interests = listOf("러닝", "헬스", "여행"),
        ),
        UserProfile(
            id = "3",
            name = "서연",
            age = 25,
            bio = "그림 그리고 전시 보러 다니는 거 좋아해요.",
            imageUrl = "https://picsum.photos/seed/meetgo3/600/800",
            interests = listOf("전시", "드로잉", "독서"),
        ),
    )

    val matches = listOf(
        MatchPreview(
            id = "m1",
            name = "지은",
            imageUrl = "https://picsum.photos/seed/meetgo1/200/200",
            lastMessage = "이번 주말에 시간 괜찮으세요?",
            isUnread = true,
        ),
        MatchPreview(
            id = "m2",
            name = "하윤",
            imageUrl = "https://picsum.photos/seed/meetgo4/200/200",
            lastMessage = "네 좋아요 :)",
        ),
        MatchPreview(
            id = "m3",
            name = "도윤",
            imageUrl = "https://picsum.photos/seed/meetgo5/200/200",
            lastMessage = "어떤 영화 좋아하세요?",
        ),
    )

    val myProfile = UserProfile(
        id = "me",
        name = "재욱",
        age = 28,
        bio = "새로운 사람 만나는 거 좋아합니다.",
        imageUrl = "https://picsum.photos/seed/meetgome/600/800",
        interests = listOf("여행", "사진", "커피"),
    )

    fun chatMessages(matchId: String): List<ChatMessage> = listOf(
        ChatMessage(id = "1", text = "안녕하세요! 반가워요 😊", isMine = false),
        ChatMessage(id = "2", text = "안녕하세요! 프로필 보고 관심사가 비슷해서 반가워요", isMine = true),
        ChatMessage(id = "3", text = "이번 주말에 시간 괜찮으세요?", isMine = false),
    )
}
