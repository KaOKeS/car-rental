const apiUrl = 'http://localhost:8080/api';

async function fetchCars() {
    try{
        var response = await fetch(`${apiUrl}/cars`);

          if(!response.ok){
            throw new Error(`Failed to fetch cars: ${response.status}`);
          }
          const cars = await response.json();
          console.log("cars:",cars);
          return cars;
    } catch(e){
        console.log(e);
    }
}

async function listsCars(carsContainerElementId) {
    const carsContainerElement = document.getElementById(carsContainerElementId);

    if(!carsContainerElement){
        console.log('Cars container element empty');
        return;
    }

    fetchCars()
        .then(cars => {
            if(!cars){
                carsContainerElement.innerHTML = 'No cars';
                return;
            }
            for(const car of cars){
                carsContainerElement.appendChild(carElement(car));
            }

        }).catch(e => {
            console.log(e);
        })
}

function carElement(car){
    const carRowElement = document.createElement('tr');
    for (const carProperty in car){
        if(car.hasOwnProperty(carProperty)){
            if(!(carProperty=="deleted" || carProperty=="rate" || carProperty=="id")){
                var createdTd = document.createElement('td');
                createdTd.innerText = capitalizeFirstLetter(`${car[carProperty]}`);
                carRowElement.appendChild(createdTd);
            }
        }
      }

    return carRowElement;
}

function capitalizeFirstLetter(str){
    return str.charAt(0).toUpperCase() + str.slice(1);
}