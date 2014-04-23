$(function(){
	
	var CardModel = Backbone.Model.extend({
		
		defaults: function() {
			return {
				id: "no id"
			};
		}
	});
	
	var Response = Backbone.Collection.extend({
		model: CardModel,
		url: "/command",
		
		parse: function (response) {
			console.log(response);
			console.log(response['data']);
			
			var idArray = _.pluck(response['data'], "id");
			console.log(idArray);
			
			return this.model = idArray;
		}
		
	});
	
	var response = new Response
	
	
	var AppView = Backbone.View.extend({
		el: $("#graph-search"),
		
		events: {
			"keypress #graph-search-input": "searchOnEnter"
		},
		
	    initialize: function() {

	        this.input = this.$("#graph-search-input");
	        
	        this.results = this.$("#graph-search-results")

	    },
		
		searchOnEnter: function(e) {
			console.log(e);
			if (e.keyCode != 13) return;
			if (!this.input.val()) return;
			
			
			
			response.fetch({data:{text: this.input.val()}, type:'POST'});
			
			
			
			this.input.val('');
		}
	})
	
	var appView = new AppView;
	
});