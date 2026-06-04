document.addEventListener("DOMContentLoaded", () => {

    let sortState = {
        key: null,
        asc: true
    };

    const themeButton = document.getElementById("themeToggle");
    const body = document.body;

    const modal = document.getElementById("detailsModal");
    const closeButton = document.querySelector(".close");
    const saveBtn = document.getElementById("saveBtn");

    const allRows = Array.from(document.querySelectorAll(".salon-row"));

    if (themeButton) {
        themeButton.addEventListener("click", () => {
            body.classList.toggle("dark");

            themeButton.innerText =
                body.classList.contains("dark")
                    ? "☀️ Light Mode"
                    : "🌙 Dark Mode";
        });
    }

    const filters = {
        name: document.getElementById("filterName"),
        district: document.getElementById("filterDistrict"),
        rating: document.getElementById("filterRating"),
        price: document.getElementById("filterPrice")
    };

    Object.values(filters).forEach(input => {
        if (input) input.addEventListener("input", applyFiltersAndSort);
    });

    document.querySelectorAll("th[data-key]").forEach(th => {

        th.style.cursor = "pointer";

        th.addEventListener("click", () => {

            const key = th.dataset.key;

            if (sortState.key === key) {
                sortState.asc = !sortState.asc;
            } else {
                sortState.key = key;
                sortState.asc = true;
            }

            applyFiltersAndSort();
        });
    });

    function applyFiltersAndSort() {

        const nameF = (filters.name?.value || "").trim().toLowerCase();
        const districtF = (filters.district?.value || "").trim().toLowerCase();
        const ratingF = parseFloat(filters.rating?.value);
        const priceF = (filters.price?.value || "").trim().toLowerCase();

        let filtered = allRows.filter(row => {

            const name = (row.dataset.name || "").toLowerCase();
            const district = (row.dataset.district || "").toLowerCase();
            const rating = parseFloat(row.dataset.rating || "0");
            const price = (row.dataset.price || "").toLowerCase();

            if (nameF && !name.includes(nameF)) return false;
            if (districtF && !district.includes(districtF)) return false;
            if (!isNaN(ratingF) && rating < ratingF) return false;
            if (priceF) {

                const priceMap = {
                    "1": "$",
                    "2": "$$",
                    "3": "$$$",
                    "4": "$$$$"
                };

                const expected = priceMap[priceF];

                if (expected && price !== expected) {
                    return false;
                }
            }

            return true;
        });

        if (sortState.key) {

            filtered.sort((a, b) => {

                let va = a.dataset[sortState.key];
                let vb = b.dataset[sortState.key];

                if (sortState.key === "rating") {
                    va = parseFloat(va || "0");
                    vb = parseFloat(vb || "0");
                } else {
                    va = (va || "").toLowerCase();
                    vb = (vb || "").toLowerCase();
                }

                if (va < vb) return sortState.asc ? -1 : 1;
                if (va > vb) return sortState.asc ? 1 : -1;
                return 0;
            });
        }

        const tbody = document.querySelector("tbody");
        if (!tbody) return;

        tbody.innerHTML = "";

        filtered.forEach(row => tbody.appendChild(row));

        if (filtered.length === 0) {
            const tr = document.createElement("tr");
            tr.innerHTML = `<td colspan="4" style="text-align:center;">No salons found</td>`;
            tbody.appendChild(tr);
        }
    }

    if (closeButton && modal) {
        closeButton.addEventListener("click", () => modal.classList.add("hidden"));

        window.addEventListener("click", event => {
            if (event.target === modal) modal.classList.add("hidden");
        });
    }

    if (saveBtn) {
        saveBtn.addEventListener("click", async () => {

            const id = document.getElementById("editId").value;

            const payload = {
                name: document.getElementById("editName").value,
                address: document.getElementById("editAddress").value,
                district: document.getElementById("editDistrict").value,
                phone: document.getElementById("editPhone").value,
                website: document.getElementById("editWebsite").value,
                rating: parseFloat(document.getElementById("editRating").value) || null,
                reviewsCount: parseInt(document.getElementById("editReviews").value) || null,
                priceRange: document.getElementById("editPrice").value
            };

            await fetch(`/api/salons/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            const row = document.querySelector(`.salon-row[data-id="${id}"]`);

            if (row) {
                row.dataset.name = payload.name;
                row.dataset.address = payload.address;
                row.dataset.district = payload.district;
                row.dataset.phone = payload.phone;
                row.dataset.website = payload.website;
                row.dataset.rating = payload.rating;
                row.dataset.reviews = payload.reviewsCount;
                row.dataset.price = payload.priceRange;

                row.children[0].innerText = payload.name;
                row.children[1].innerText = payload.district;
                row.children[2].innerText = payload.rating;
                row.children[3].innerText = payload.priceRange;
            }

            if (modal) modal.classList.add("hidden");

            applyFiltersAndSort();
        });
    }

    applyFiltersAndSort();

});