Room-of-Requirement (Reactor StoryGraph Voice/Text Search Engine) 

Endpoints

Endpoint:	 /search
Request Type: 	GET/POST
Parameters: 
		text - Search query in text format
Response Format(JSON):

{
	"status":"",
	"response_time": 0,
	"response_type": "",
	"data":[
		{[
			{
				"type": "news"
			},
			{
				"type": "news
			}
		]},
		{[
			{
				"type": "wikipedia"
			}
		]},
		{[
			{
				"type":"twitter"
			},
			{
				"type":"twitter"
			},
			{
				"type":"twitter"
			}
		]}]
}

Response Description:
		status - status/error code
		response_time - response time in miliseconds
		response_type -	action type code (for Winston App integration)
		data - An array of sets(nested array) of story/card objects related to query




