
define(['userModel/UserModel',
        'commonController/MainController',
        'commonTool/tool'
       ], function(UserModel, MainController,tool){
    var MyBusinessController = MainController.extend({
    	

        module: 'user',
        name: 'business',
        actions: {
            'login': 'login',
           
        },
        init: function(){
        	this.userModel = new UserModel();
        	
        },
 
    });
    return MyBusinessController;
});
