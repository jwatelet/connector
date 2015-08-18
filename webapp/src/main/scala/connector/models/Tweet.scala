package connector.models

import java.util.Date

case class Tweet(id: BigInt, user: String, text: String, createdAt: Date)
