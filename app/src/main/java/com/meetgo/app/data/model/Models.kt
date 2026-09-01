package com.meetgo.app.data.model

data class UserProfile(
    val id: String,
    val name: String,
    val age: Int,
    val bio: String,
    val photos: List<String>,
    val interests: List<String> = emptyList(),
    val distanceKm: Double = 0.0,
) {
    val mainPhoto: String get() = photos.first()
}

data class MatchPreview(
    val id: String,
    val otherUser: UserProfile,
    val lastMessage: String,
    val isUnread: Boolean = false,
)

data class ChatMessage(
    val id: String,
    val text: String? = null,
    val photoUrl: String? = null,
    val isMine: Boolean,
)

enum class ReportReason(val label: String) {
    SPAM("스팸/광고"),
    INAPPROPRIATE_PHOTO("부적절한 사진"),
    SUSPECTED_FRAUD("사기 의심"),
    OTHER("기타"),
}

object SampleData {
    val discoverProfiles = listOf(
        UserProfile(
            id = "1",
            name = "지은",
            age = 27,
            bio = "주말엔 등산, 평일엔 카페 투어 좋아해요.",
            photos = listOf(
                "https://picsum.photos/seed/meetgo1/600/800",
                "https://picsum.photos/seed/meetgo1b/600/800",
            ),
            interests = listOf("등산", "카페", "영화"),
            distanceKm = 1.2,
        ),
        UserProfile(
            id = "2",
            name = "민준",
            age = 29,
            bio = "러닝 크루 운영 중입니다. 같이 뛰실 분!",
            photos = listOf("https://picsum.photos/seed/meetgo2/600/800"),
            interests = listOf("러닝", "헬스", "여행"),
            distanceKm = 3.5,
        ),
        UserProfile(
            id = "3",
            name = "서연",
            age = 25,
            bio = "그림 그리고 전시 보러 다니는 거 좋아해요.",
            photos = listOf(
                "https://picsum.photos/seed/meetgo3/600/800",
                "https://picsum.photos/seed/meetgo3b/600/800",
                "https://picsum.photos/seed/meetgo3c/600/800",
            ),
            interests = listOf("전시", "드로잉", "독서"),
            distanceKm = 5.8,
        ),
    )

    val matches = listOf(
        MatchPreview(
            id = "m-1",
            otherUser = discoverProfiles[0],
            lastMessage = "이번 주말에 시간 괜찮으세요?",
            isUnread = true,
        ),
        MatchPreview(
            id = "m-3",
            otherUser = discoverProfiles[2],
            lastMessage = "안녕하세요! 반가워요",
            isUnread = true,
        ),
        MatchPreview(
            id = "m-4",
            otherUser = UserProfile(
                id = "4",
                name = "하윤",
                age = 26,
                bio = "여행 다니는 거 좋아해요.",
                photos = listOf("https://picsum.photos/seed/meetgo4/600/800"),
                interests = listOf("여행", "맛집"),
                distanceKm = 2.1,
            ),
            lastMessage = "네 좋아요 :)",
        ),
        MatchPreview(
            id = "m-5",
            otherUser = UserProfile(
                id = "5",
                name = "도윤",
                age = 30,
                bio = "영화 보는 걸 좋아합니다.",
                photos = listOf("https://picsum.photos/seed/meetgo5/600/800"),
                interests = listOf("영화", "OST"),
                distanceKm = 7.4,
            ),
            lastMessage = "어떤 영화 좋아하세요?",
        ),
    )

    val myProfile = UserProfile(
        id = "me",
        name = "재욱",
        age = 28,
        bio = "새로운 사람 만나는 거 좋아합니다.",
        photos = listOf("https://picsum.photos/seed/meetgome/600/800"),
        interests = listOf("여행", "사진", "커피"),
    )

    fun findProfile(userId: String): UserProfile =
        discoverProfiles.find { it.id == userId }
            ?: matches.find { it.otherUser.id == userId }?.otherUser
            ?: myProfile

    fun chatMessages(matchId: String): List<ChatMessage> = listOf(
        ChatMessage(id = "1", text = "안녕하세요! 반가워요 😊", isMine = false),
        ChatMessage(id = "2", text = "안녕하세요! 프로필 보고 관심사가 비슷해서 반가워요", isMine = true),
        ChatMessage(id = "3", text = "이번 주말에 시간 괜찮으세요?", isMine = false),
    )

    val profileIdsWhoLikeMe = setOf("1", "3")

    val interestOptions = listOf(
        "등산", "카페", "영화", "러닝", "헬스", "여행",
        "전시", "드로잉", "독서", "맛집", "OST", "사진",
    )
}
