



data class Release (

	val url : String,
	val assets_url : String,
	val upload_url : String,
	val html_url : String,
	val id : Int,
	val node_id : String,
	val tag_name : String,
	val target_commitish : String,
	val name : String,
	val draft : Boolean,
	val author : Author,
	val prerelease : Boolean,
	val created_at : String,
	val published_at : String,
	val assets : List<Assets>,
	val tarball_url : String,
	val zipball_url : String,
	val body : String
)