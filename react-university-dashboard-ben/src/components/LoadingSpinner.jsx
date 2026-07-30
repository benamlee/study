function LoadingSpinner({ message = '載入中'}) {
    return (
        <div className="flex flex-col items-center justify-center py-20">
            <div className="w-12 h-12 border-4 border-blue-200 border-t-blue-500 rounded-full animate-spin" />
            <p className="mt-4 text-gray-500 text-sm">{message}</p>
        </div>
    )
}
export default LoadingSpinner;