const apiUrl = 'http://localhost:8080/api';

async function fetchCars(restUrl) {
    try{
        var response = await fetch(`http://localhost:8080/api${restUrl}`);

          if(!response.ok){
            throw new Error(`Failed to fetch cars: ${response.status}`);
          }
          const cars = await response.json();
          return cars;
    } catch(e){
        console.log(e);
    }
}

async function listsCars(carsContainerElementId,restUrl,isAdmin) {
    const carsContainerElement = document.getElementById(carsContainerElementId);

    if(!carsContainerElement){
        console.log('Cars container element empty');
        return;
    }

    fetchCars(restUrl)
        .then(cars => {
            if(!cars){
                carsContainerElement.innerHTML = 'No cars';
                return;
            }
            for(const car of cars){
                carsContainerElement.appendChild(carElement(car,isAdmin));
            }

        }).catch(e => {
            console.log(e);
        })
}

function carElement(car,isAdmin){
    const carRowElement = document.createElement('tr');
    for (const carProperty in car){
        if(car.hasOwnProperty(carProperty)){
            if(!(carProperty=="deleted" || carProperty=="rate" || carProperty=="id" ||
             carProperty=="carDescription" || carProperty=="imagePath" || carProperty=="rentalDtos"
             || carProperty=="default_IMAGE_PATH")){
                var createdTd = document.createElement('td');
                if(carProperty=="sittingPlaces"){
                    createdTd.setAttribute("class","align-middle text-center");
                    createdTd.innerText = capitalizeFirstLetter(`${car[carProperty]}`);
                    carRowElement.appendChild(createdTd);

                }else{
                    createdTd.setAttribute("class","align-middle");
                    createdTd.innerText = capitalizeFirstLetter(`${car[carProperty]}`);
                    carRowElement.appendChild(createdTd);
                }
                
            }
            else if(carProperty=="imagePath"){
                var createdTd = document.createElement('td');
                
                var createdImage = new Image();
                createdImage.src=`${car[carProperty]}`;
                createdImage.className="img-fluid";
                
                createdTd.appendChild(createdImage)
                carRowElement.appendChild(createdTd);
            }
        }
    }
    if(isAdmin){
        var createdTd = document.createElement('td');
        createdTd.setAttribute("class","align-middle col-sm-2");
        createdTd.setAttribute("sec:authorize","hasRole('ROLE_ADMIN')");
        var createdA = document.createElement('a');
        createdA.setAttribute("href","/management/admin/updateCar/"+`${car["id"]}`);
        createdA.setAttribute("class","btn btn-block btn-primary btn-sm me-1");
        createdA.innerText = "Update";
        createdTd.appendChild(createdA);
        var createdA1 = document.createElement('a');
        createdA1.setAttribute("href","/management/admin/deleteCar/"+`${car["id"]}`);
        createdA1.setAttribute("class","btn btn-block btn-danger btn-sm");
        createdA1.innerText = "Delete";
        createdTd.appendChild(createdA1);
        carRowElement.appendChild(createdTd); 
        var createdTd = document.createElement('td');
        createdTd.setAttribute("class","align-middle");
        var createdA2 = document.createElement('a');
        createdA2.setAttribute("href","/rent/"+`${car["id"]}`);
        createdA2.setAttribute("class","btn btn-block btn-secondary btn-sm me-1");
        createdA2.innerText = "Rent";
        createdTd.appendChild(createdA2);
        carRowElement.appendChild(createdTd);
    }
    else{
        var createdTd = document.createElement('td');
        createdTd.setAttribute("class","align-middle");
        var createdA = document.createElement('a');
        createdA.setAttribute("href","/rent/"+`${car["id"]}`);
        createdA.setAttribute("class","btn btn-block btn-secondary btn-sm me-1");
        createdA.innerText = "Rent";
        createdTd.appendChild(createdA);
        carRowElement.appendChild(createdTd);
    }


    return carRowElement;
}

function capitalizeFirstLetter(str){
    return str.charAt(0).toUpperCase() + str.slice(1);
}

function fillFieldsWithRequestParams(){
    var reqPageNumber = null;
    var reqPerPage = null;
    var reqType = null;
    var reqStartDate = null;
    var reqEndDate = null;
    var reqSearchBy = null;
    var reqMaxPrice = null;
    var reqMinPrice = null;
    var reqOrderBy = null;
    var reqSearchQuery = null;

    //Get URL Params
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    if(urlParams.has("pageNumber")){
        reqPageNumber = urlParams.get('pageNumber');
    }
    if(urlParams.has("perPage")){
        reqPerPage = urlParams.get('perPage');
    }
    if(urlParams.has("type")){
        reqType = urlParams.get('type');
    }
    if(urlParams.has("startDate")){
        reqStartDate = urlParams.get('startDate');
    }
    if(urlParams.has("endDate")){
        reqEndDate = urlParams.get('endDate');
    }
    if(urlParams.has("searchBy")){
        reqSearchBy = urlParams.get('searchBy');
    }
    if(urlParams.has("maxPrice")){
        reqMaxPrice = urlParams.get('maxPrice');
    }
    if(urlParams.has("minPrice")){
        reqMinPrice = urlParams.get('minPrice');
    }
    if(urlParams.has("orderBy")){
        reqOrderBy = urlParams.get('orderBy');
    }
    if(urlParams.has("search")){
        reqSearchQuery = urlParams.get('search');
    }
    
    if(reqType!=null)
        document.getElementById('typeChose').value=reqType;
    if(reqPerPage!=null)
        document.getElementById('itemsPerPage').value=reqPerPage;
    if(reqStartDate!=null)
        document.getElementById('startDate').value=reqStartDate;
    if(reqEndDate!=null)
        document.getElementById('endDate').value=reqEndDate;
    if(reqSearchBy!=null){
        document.getElementById(reqSearchBy).checked=true
    }
    if(reqSearchQuery!=null){
        document.getElementById('searchField').value=reqSearchQuery;
    }
    if(reqMaxPrice!=null){
        document.getElementById('maxPrice').value=reqMaxPrice;
    }
    if(reqMinPrice!=null){
        document.getElementById('minPrice').value=reqMinPrice;
    }
    if(reqOrderBy!=null){
        document.getElementById(reqOrderBy).checked=true
    }

}