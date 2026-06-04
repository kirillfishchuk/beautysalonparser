const themeButton = document.getElementById("themeToggle");
const body = document.body;

themeButton.addEventListener("click", () => {

    body.classList.toggle("dark");

    if (body.classList.contains("dark")) {
        themeButton.innerText = "☀️ Light Mode";
    } else {
        themeButton.innerText = "🌙 Dark Mode";
    }
});

const filterInput = document.getElementById("districtFilter");

filterInput.addEventListener("input", () => {

    const filter =
        filterInput.value.toLowerCase();

    document
        .querySelectorAll(".salon-row")
        .forEach(row => {

            const district =
                row.dataset.district.toLowerCase();

            row.style.display =
                district.includes(filter)
                    ? ""
                    : "none";
        });
});

const modal =
    document.getElementById("detailsModal");

const closeButton =
    document.querySelector(".close");

document
    .querySelectorAll(".salon-row")
    .forEach(row => {

        row.addEventListener("click", () => {

            document.getElementById("modalName").innerText =
                row.dataset.name;

            document.getElementById("modalAddress").innerText =
                row.dataset.address;

            document.getElementById("modalDistrict").innerText =
                row.dataset.district;

            document.getElementById("modalPhone").innerText =
                row.dataset.phone;

            document.getElementById("modalRating").innerText =
                row.dataset.rating;

            document.getElementById("modalReviews").innerText =
                row.dataset.reviews;

            document.getElementById("modalPrice").innerText =
                row.dataset.price;

            const website =
                document.getElementById("modalWebsite");

            website.href = row.dataset.website;
            website.innerText = row.dataset.website;

            modal.classList.remove("hidden");
        });
    });

closeButton.addEventListener("click", () => {
    modal.classList.add("hidden");
});

window.addEventListener("click", event => {

    if (event.target === modal) {
        modal.classList.add("hidden");
    }
});