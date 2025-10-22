public class RecetasBottomSheet extends BottomSheetDialogFragment {
    private RecetasBottomSheetBinding binding;
    private RecetarioViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = RecetasBottomSheetBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(RecetarioViewModel.class);

        // Observar la receta seleccionada
        viewModel.getRecetaSeleccionada().observe(getViewLifecycleOwner(), receta -> {
            if (receta != null) {
                binding.nombreReceta.setText(receta.getNombre());
            }
        });

        // Ejemplo: eliminar desde aquí
        binding.opcionEliminar.setOnClickListener(v -> {
            Receta receta = viewModel.getRecetaSeleccionada().getValue();
            if (receta != null) {
                // Llamada al método del ViewModel
                viewModel.eliminarReceta(receta);
                dismiss();
            }
        });

        return binding.getRoot();
    }
}
