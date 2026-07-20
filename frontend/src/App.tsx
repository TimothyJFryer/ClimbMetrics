import Navbar from "./components/Navbar";
import Home from "./pages/Home.tsx";

function App() {
    return (
        <div>
            <Navbar />
            <Home />
            <main>
                <h1>Welcome to CruxVision</h1>
            </main>
        </div>
    );
}

export default App;