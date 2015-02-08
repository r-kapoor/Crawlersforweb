/**
 * Created by rkapoor on 08/02/15.
 */
var routesMapModule = angular.module('tripdetails.routes.map.app', []);
routesMapModule.controller('mapController',  function($scope) {
    $scope.zoomConstants = {
        COUNTRY: 5,
        CITY: 12
    };
    $scope.centerPosition = {
        Latitude: 25.4411179,
        Longitude: 78.5622883
    };
    var markerPosition = {
        Latitude: 28.6139,
        Longitude: 77.2089
    };
    var markerPosition1 = {
        Latitude: 12.9667,
        Longitude: 77.5667
    };
    var encodedPath = "kunmDw{ovMxhCgXfyEy_DjrEsuAbskBquKxbFemAdeJo_Fb~KcdFx|Ba}Bzts@kgg@piNzDds`@sp]|}Fik^xrD{MbvTloDheAvl@x}LzcBr{AgIzoo@~iGz~CevBjBehAv~DkaCb|BhqAhjTg}G`vJwnGlkIgfHt~D_a@b~TggMl}E_}F`jJzeCbcQe}DlpQ{sGpsMy}JvjNgvHd`AkeBh{W{uGhiBcQfdBokChkQksGxuBcTpsHohHzgD}rAz`EtIjoGrfBv{D|lC|cSz`IpdDfc@`rB||BzqRd`IviBqcBhyG{\\v}JgsCb_FdEh_JvwCzk[diAnoLwa@t~WdjIpxKfbAdjCteBneGqQlmBtoBf{KjhCvbGvwDdrP~kG`vGdoB~dG`nKfwGjvD`mUx{F|_B~cAfg@bpCzvVr_Nj~r@n}PxsI`mJqFjuBz{IbgWne@fbFzsAfuBfzDnqBxxDxdFlJvuEhoCpm@leHfiKhrGlcCzqAktBhtJgeBrnBsv@pnQa_LfpQehM~hDsxFbzCofAxmG}A|gCr`ChoEcnBrfAg|DbaCc_@ldFuiCd~BmqC|pGos@t_Je|DfiF|iAxzBaYjfD|i@ldB{t@cl@cwC~]gpCzfBmtAr{Rpi@jgD{SneLuuHjlGwS|mNo~E|uMugQrtKt`@raEzhAncIpzLzDr_CpmHpsBpoCin@nsAsjBmx@}kF{VohPyvA{zHxp@w{MzjGg_GpeFsdDhhEwuA|{JyqW~lJ|UvuDyeH~dAseI||B_sB~~Dm_G|c@o{BffGuxC`yAh~CddDwjAvcGzrCzjKcXvtKwaFpeL_lCfe@edGs`ByfLym@ssIee@qhAfo@{{EjxDujIdsDcmPsz@_dOh`AaiGxvF}_CruDaX|_D|a@rbRjeG|kI`gB|cCviArpGcGl}M|cJr\\tn@z_E`s\\peDb}R|wBjfC|jB|dKbf@{GvpMkzNjnV}t[fcCh]pjP}sG~lf@g|Y`sFcOznI{zCd_AgjAjsJm|RhoEspFnmFamNbjF}iE~iIam@~uR_vLhbEm}F~gFkqAn`JzBzdF~_AhcLklD|fFyiDhxCg|Ij_FkyCn_GpwDnzI|pBr_FpqFd{@bvEleBv{BdvDb_AzoIcf@tpHe|@pdLimF|_LcvB~w[l{E``H|Gx}GbuCzyEuz@rnM|qAl}CphBvoOz~B~e@ib@~fNcqCrlFe|DlzGq~CzlFyhDtcFp`B|~Xlc@zc]{~Dh|Ck|@~tItPrfCzoDtp]nt^trDlbIhiCpfDlsBbgKtGvvCxuMrfQz}ClfJrxGvgCbfE~tEa^dfDhjCbfBxwBfqHzeDjoDddBvnGhPfaDhwApjD{UjaUbbAp`GxDp|HleA`eDy`@b}GuoC~hDcPncG{eCf~J~{Fdpb@wMvmGtxIhpXb`ElbIbuO~pJ_`@tvDjd@~pG_{AzyEx{@ruHtdE`bPlB~cHrgCdtJ}@fbDzpGtk\\`VpaHzeH~pZ~Y`mDvaBbaEjmJthHpzA`aHvuWccG|wGm[xlJesCdw_Aido@jyGy\\lnCynBjgGrS|dFaeC`pGz[|tJgo@lwEpaAheG~dChaHb{@`bCwVtaHj~@j|Go~@r`IhcBteNbkIt{Sv~BdxM|AbeCym@dxFewGl|BqkBxjNc_E|}NqmGxiC_XdrQaW`uW|x@xpK{sAb{Fm}BfbDydHrsCqzAniC{bInkNglKj`Cyt@xuI`Xn|GyhBh`IlXnnCwYbvItNzlIy^b|BifDvbRgkJdgKwhB~`BXblCurA|qC|fArlDehCtnL_lCxoKdg@pl@cbBn`I_c@bfAd\\lmEbdIpkA~uQ~{AnoEvtF~tAteK_xB~_JdnAfcUzcJza@vhAztB~O|nPabBt|Gzb@peH}k@|lFrdAraGasBz|QfgBvkFcf@p_HcxCr~j@_~Ej`HeoCdtHxwEnWtcBdmI{gDp]`O";
    //var markerPosition = {
    //    Latitude: position.coords.latitude,
    //    Longitude: position.coords.longitude
    //};

    var markers = [];
    var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';
    $scope.map;

    $scope.zoom = $scope.zoomConstants.COUNTRY;
    $scope.initializeMap = function() {
        console.log('Hello World');
        function initialize() {
            var mapCanvas = document.getElementById('map-canvas');
            var mapOptions = {
                center: new google.maps.LatLng($scope.centerPosition.Latitude, $scope.centerPosition.Longitude),
                zoom: $scope.zoom,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            $scope.map = new google.maps.Map(mapCanvas, mapOptions);
            $scope.addMarker(markerPosition);
            $scope.addMarker(markerPosition1);
            $scope.showRoute(markerPosition, markerPosition1);
            $scope.showPath(encodedPath);
            $scope.showFlightPath(markerPosition, markerPosition1);
        }
        google.maps.event.addDomListener(window, 'load', initialize);
    };
    function setAllMap() {
        console.log('setAllMap');
        for (var i = 0; i < markers.length; i++) {
            console.log('i:'+i);
            markers[i].setMap($scope.map);
        }
    }
    $scope.initializeMap();
    $scope.addMarker = function(position) {
        console.log('addMarker');
        var marker = new google.maps.Marker({
            position: new google.maps.LatLng(position.Latitude, position.Longitude),
            map: $scope.map
        });
        //markers.push(marker);
        //setAllMap();
    };
    $scope.showRoute = function(originPosition, destinationPosition) {
        var directionsService = new google.maps.DirectionsService();
        var directionsRequest = {
            origin: new google.maps.LatLng(originPosition.Latitude, originPosition.Longitude),
            destination: new google.maps.LatLng(destinationPosition.Latitude, destinationPosition.Longitude),
            travelMode: google.maps.DirectionsTravelMode.DRIVING,
            unitSystem: google.maps.UnitSystem.METRIC
        };
        directionsService.route(
            directionsRequest,
            function(response, status)
            {
                //console.log('Response:'+JSON.stringify(response));
                if (status == google.maps.DirectionsStatus.OK)
                {
                    new google.maps.DirectionsRenderer({
                        map: $scope.map,
                        directions: response
                    });
                }
            }
        );
    };
    $scope.showPath = function(pathString) {
        var path = new google.maps.Polyline({
            path: google.maps.geometry.encoding.decodePath(pathString),
            map:$scope.map
        });
    };
    $scope.showFlightPath = function(originPosition, destinationPosition) {
        var line = new google.maps.Polyline({
            path: [new google.maps.LatLng(originPosition.Latitude, originPosition.Longitude),
                new google.maps.LatLng(destinationPosition.Latitude, destinationPosition.Longitude)],
            map: $scope.map
        });
    };
    //$scope.addMarker();
});
