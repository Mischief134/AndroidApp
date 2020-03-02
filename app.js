const fs = require('fs');
const express = require('express')
const app = express()
const port = 3001

const storePath = __dirname + '/games.json';
// Convert the store JSON file into an object
function getJson(path)
{
	const content = fs.readFileSync(path, 'utf-8');
	return JSON.parse(content);
}

// Search from the JSON object
function search(query)
{
	const store = getJson(storePath);
	const result = [];
	// Convert the string to all lowercase because queries shouldn't be case-sensitive
	query = query.toLowerCase();
	// Iterate through the object and query
	store.forEach((e) => {
		if (e.title.toLowerCase().indexOf(query) !== -1) {
			result.push({
				val: e,
				rank: (query.length / e.title.length) * 2,
			});
		}
		else if (e.subgenre.toLowerCase().indexOf(query) !== -1) {
			result.push({
				val: e,
				rank: (query.length / e.title.length)
			});
		}
	});
	
	// Sort the result by rank
	result.sort((a, b) => a.rank - b.rank);
	
	return result.map(e => e.val);
}

app.get('/search', (req, res) => res.sendFile(storePath));

app.listen(port, () => console.log(`Listening on port ${port}!`));
