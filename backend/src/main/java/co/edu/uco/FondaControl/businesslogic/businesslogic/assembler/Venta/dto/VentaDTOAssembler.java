package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Venta.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.FormaPagoDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.MesaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SesionTrabajoDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.TipoVentaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.VentaDomain;
import co.edu.uco.FondaControl.dto.VentaDTO;

public final class VentaDTOAssembler implements DTOAssembler<VentaDTO, VentaDomain> {

	private static final VentaDTOAssembler INSTANCIA = new VentaDTOAssembler();

	private VentaDTOAssembler() {
		super();
	}

	public static VentaDTOAssembler getInstancia() {
		return INSTANCIA;
	}

	@Override
	public VentaDTO toDto(final VentaDomain domain) {
		if (domain == null) {
			return new VentaDTO();
		}

		return VentaDTO.builder().codigo(domain.getCodigoVenta()).fecha(domain.getFecha())
				.totalVenta(domain.getTotalVenta())
				.codigoFormaPago(domain.getFormaPago() != null ? domain.getFormaPago().getCodigo() : null)
				.codigoTipoVenta(domain.getTipoVenta() != null ? domain.getTipoVenta().getCodigo() : null)
				.codigoSesionTrabajo(domain.getSesionTrabajo() != null ? domain.getSesionTrabajo().getCodigo() : null)
				.codigoMesa(domain.getMesa() != null ? domain.getMesa().getCodigo() : null).crear();
	}

	@Override
	public VentaDomain toDomain(final VentaDTO dto) {
		if (dto == null) {
			return VentaDomain.obtenerValorDefecto();
		}

		return new VentaDomain(dto.getCodigo(), dto.getFecha(), dto.getTotalVenta(),
				dto.getCodigoFormaPago() != null ? new FormaPagoDomain(dto.getCodigoFormaPago(), "") : null,
				dto.getCodigoTipoVenta() != null ? new TipoVentaDomain(dto.getCodigoTipoVenta(), "") : null,
				dto.getCodigoSesionTrabajo() != null
						? new SesionTrabajoDomain(dto.getCodigoSesionTrabajo(), null, null, null, null)
						: null,
				dto.getCodigoMesa() != null ? new MesaDomain(dto.getCodigoMesa(), "", null) : null);
	}

	@Override
	public List<VentaDTO> toDtoList(final List<VentaDomain> domainList) {
		final List<VentaDTO> resultado = new ArrayList<>();
		for (VentaDomain domain : domainList) {
			resultado.add(toDto(domain));
		}
		return resultado;
	}

	@Override
	public List<VentaDomain> toDomainList(final List<VentaDTO> dtoList) {
		final List<VentaDomain> resultado = new ArrayList<>();
		for (VentaDTO dto : dtoList) {
			resultado.add(toDomain(dto));
		}
		return resultado;
	}
}
