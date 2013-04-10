package pt.ist.ave.jzx.Instructions;

public class JR_CD extends Instruction {

	private int _tstates;
	public JR_CD(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		if (_cpu.getM_carryF()) {
			_tstates = 12;
			int m_pc16 = _cpu.getM_pc16(); 
			_cpu.setM_pc16(_cpu.add16(m_pc16, (byte) _cpu.getM_memory().read8(m_pc16) + 1));
		} else {
			_tstates = 7;
			_cpu.inc16pc();
		}
	}

	@Override
	public int incTstates() {
		return _tstates;
	}

}
