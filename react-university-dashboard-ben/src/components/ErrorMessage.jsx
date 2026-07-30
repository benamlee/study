function ErrorMessage({ message, onRetry }) {
    if (!message) return null;

    return (
        <div className="bg-red-50 border border-red-200 rounded-lg p-4">
            <div className="flex items-start gap-3">
                <span className="text-2xl">⚠️</span>
                <div className="flex-1">
                    <h3 className="text-red-700 font-medium text-sm">發生錯誤</h3>
                    <p className="text-red-600 text-sm mt-1">{message}</p>
                </div>
                {onRetry && (
                    <button
                        onClick={onRetry}
                        className="bg-red-100 text-red-600 px-4 py-1.5 rounded text-sm
                                    hover:bg-red-200 transition-colors whitespace-nowrap"
                    >
                        重試
                    </button>
                )}
            </div>
        </div>
    );
}

export default ErrorMessage;