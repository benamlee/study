function Header({ title, subtitle }) {
    return (
        <header className="bg-gradient-to-r from-blue-500 to-purple-600 text-white py-6 px-8 shadow-lg">
            <div className="max-w-5xl mx-auto">
                <h1 className="text-3xl font-bold">{title}</h1>
                {subtitle && (
                    <p className="text-blue-100 mt-1 text-sm">{subtitle}</p>
                )}
            </div>
        </header>
    );
}

export default Header;