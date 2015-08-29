/**
 * Created by rajat on 4/26/2015.
 */

(function() {
    angular.module("ngFixToTop", []);

    angular.module("ngFixToTop").directive("fixTop", [
         function() {
            return {
                restrict: 'A',
                link: function($scope, $elem) {
                    var fixmeTop = $elem.offset().top;
                    var width = $elem.width();
                    console.log("id:"+$elem.attr('id'));
                    console.log("device width:"+$(window).width());

                    if(!($(window).width()<=991 && $elem.attr('id')=='map-canvas')){
                        jQuery(window).scroll(function() {
                            var currentScroll = jQuery(window).scrollTop();
                            if (currentScroll >= fixmeTop) {
                                $elem.css({
                                    position: 'fixed',
                                    top: '0',
                                    zIndex:'200',
                                    width:width
                                    //left: '0'
                                });
                            } else {
                                $elem.css({
                                    position: 'static'
                                });
                            }
                        });
                    }
                }
            };
         }
    ]);

    angular.module("ngFixToTop").directive("fixTopMobile", [
        function() {
            return {
                restrict: 'A',
                link: function($scope, $elem) {
                    var fixmeTop = $elem.offset().top;
                    //var width = $elem.width();
                    jQuery(window).scroll(function() {
                        var currentScroll = jQuery(window).scrollTop();
                        if (currentScroll >= fixmeTop) {
                            $elem.css({
                                position: 'fixed',
                                top: '0',
                                left:'0',
                                zIndex:'200',
                                width:'100%'
                                //left: '0'
                            });
                        }
                        else {
                            $elem.css({
                                position: 'static'
                            });
                        }
                    });
                }
            };
        }
    ]);

}).call(this);
